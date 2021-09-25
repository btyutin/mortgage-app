package com.wavesplatform.we.app.mortgage.contract.impl

import com.wavesplatform.vst.contract.data.ContractCall
import com.wavesplatform.vst.contract.mapping.Mapping
import com.wavesplatform.vst.contract.spring.annotation.ContractHandlerBean
import com.wavesplatform.vst.contract.state.ContractState
import com.wavesplatform.vst.contract.state.getValue
import com.wavesplatform.vst.contract.state.set
import com.wavesplatform.vst.contract.state.setValue
import com.wavesplatform.we.app.mortgage.contract.MortgageContract
import com.wavesplatform.we.app.mortgage.contract.OfferAccept
import com.wavesplatform.we.app.mortgage.domain.InsuranceAgreement
import com.wavesplatform.we.app.mortgage.domain.InsuranceAgreementStatus.ACTIVE
import com.wavesplatform.we.app.mortgage.domain.InsuranceAgreementStatus.NEW
import com.wavesplatform.we.app.mortgage.domain.InsuranceAgreementStatus.WAIT_CLIENT
import com.wavesplatform.we.app.mortgage.domain.InsuranceOffer
import com.wavesplatform.we.app.mortgage.domain.InsuranceOfferStatus.ACCEPTED
import com.wavesplatform.we.app.mortgage.domain.InsuranceOfferStatus.DECLINED
import com.wavesplatform.we.app.mortgage.domain.InsuranceOfferStatus.OFFERED
import com.wavesplatform.we.app.mortgage.domain.InsurancePolice
import com.wavesplatform.we.app.mortgage.domain.InsuranceRisk
import com.wavesplatform.we.app.mortgage.domain.InsuranceRisk.PROPERTY
import com.wavesplatform.we.app.mortgage.domain.MortgageAgreement
import com.wavesplatform.we.app.mortgage.domain.MortgageAgreementStatus
import com.wavesplatform.we.app.mortgage.domain.MortgageAgreementStatus.WAIT_FOR_PAYMENT
import com.wavesplatform.we.app.mortgage.domain.PoliceStatus
import com.wavesplatform.we.app.mortgage.domain.formula.PropertyFormula
import org.apache.commons.lang3.time.DateUtils
import java.math.BigDecimal
import java.util.Date
import java.util.UUID

@ContractHandlerBean
class MortgageContractImpl(
    state: ContractState,
    private val caller: ContractCall
) : MortgageContract {

    // Participants roles
    //
    private var banks: List<String>? by state
    private var companies: List<String>? by state
    private var operator: String? by state

    // Store accreditation of insurance company to bank
    //
    private val accreditation: Mapping<List<String>> by state

    // Insurance company has a formula to compute risk type
    //
    private val formulas: Mapping<PropertyFormula> by state

    // Agreement mapping
    //
    private val agreements: Mapping<MortgageAgreement> by state

    // Offer mapping and index by mortgage document number
    //
    private val offers: Mapping<InsuranceOffer> by state
    private val offerIndex: Mapping<List<String>> by state

    // Insurance agreement mapping
    //
    private val insurances: Mapping<InsuranceAgreement> by state
    private val insuranceIndex: Mapping<List<String>> by state

    // Police mapping
    //
    private val polices: Mapping<InsurancePolice> by state
    private val policeIndex: Mapping<MutableList<String>> by state

    override fun create(banks: List<String>, companies: List<String>) {
        require(banks.isNotEmpty()) { "BANKS_EMPTY" }
        require(companies.isNotEmpty()) { "COMPANIES_EMPTY" }
        require(banks.intersect(companies).isEmpty()) { "DUPLICATE_KEYS_IN_ROLES" }

        this.banks = banks
        this.companies = companies
        this.operator = caller.sender
    }

    override fun setRiskFormula(formula: PropertyFormula, type: InsuranceRisk) {
        require(companies!!.contains(caller.sender)) { "NOT_INSURANCE_COMPANY" }

        formulas["${caller.sender}_$type"] = formula
    }

    override fun setInsuranceCompanyForBank(companies: List<String>) {
        require(this.companies!!.containsAll(companies)) { "NOT_INSURANCE_COMPANY_IN_LIST" }
        require(this.banks!!.contains(caller.sender)) { "NOT_BANK" }

        accreditation[caller.sender] = companies
    }

    override fun createMortgageAgreement(agreement: MortgageAgreement) {
        require(!agreements.has(agreement.documentNumber)) { "DUPLICATE_DOCUMENT" }
        require(banks!!.contains(caller.sender)) { "NOT_BANK" }
        require(agreement.bankAddress == caller.sender) { "WRONG_BANK" }

        agreements[agreement.documentNumber] = agreement

        // Automatically compute offers
        //
        val offerIds = mutableListOf<String>()
        accreditation[caller.sender]!!.forEach { c ->
            InsuranceRisk.values().forEach { r ->
                val formula = formulas["${c}_$r"]
                val result = formula.eval(agreement)
                val offer = InsuranceOffer(
                        id = sha256("${caller.id}_${c}_${r}"),
                        mortgageDocument = agreement,
                        risk = r,
                        insurerAddress = c,
                        payAmount = result,
                        status = OFFERED
                )
                offers[offer.id] = offer
                offerIds.add(offer.id)
            }
        }
        offerIndex[agreement.documentNumber] = offerIds
    }

    override fun clientAcceptOffer(offers: List<OfferAccept>) {
        require(this.operator!! == caller.sender) { "NOT_AN_OPERATOR" }
        require(offers.isNotEmpty()) { "EMPTY_OFFER_LIST" }
        require(offers.any { it.risk == PROPERTY }) { "PROPERTY_RISK_SHOULD_BE_COVERED" }

        // Mark all offers as declined
        //
        val documentId = this.offers[offers[0].offerId].mortgageDocument.documentNumber
        offerIndex[documentId].forEach {
            this.offers[it] = this.offers[it].copy(
                    status = DECLINED
            )
        }

        val documentIds = mutableListOf<String>()
        var key: String? = null

        // Accept selected offers
        //
        offers.forEach {
            val offer = this.offers[it.offerId]
            this.offers[it.offerId] = offer.copy(
                    status = ACCEPTED
            )

            // Create insurance agreement
            //
            val insuranceAgreement = InsuranceAgreement(
                    documentNumber = sha256("${it.offerId}_${caller.id}"),
                    personName = offer.mortgageDocument.personName,
                    personPatronymic = offer.mortgageDocument.personPatronymic,
                    personSurname = offer.mortgageDocument.personSurname,
                    personId = offer.mortgageDocument.personId,
                    amount = offer.mortgageDocument.remainingAmount,
                    payAmount = offer.payAmount,
                    risk = offer.risk,
                    documentDate = Date(caller.timestamp),
                    payDate = BigDecimal.ZERO,
                    mortgageDocument = offer.mortgageDocument,
                    status = NEW,
                    policeNumber = "",
                    insurerAddress = offer.insurerAddress
            )
            insurances[insuranceAgreement.documentNumber] = insuranceAgreement
            key = "${offer.mortgageDocument.documentNumber}_${insuranceAgreement.personId}"
            documentIds.add(insuranceAgreement.documentNumber)
        }

        insuranceIndex[key!!] = documentIds
    }

    override fun companyAcceptsInsuranceAgreement(documentNumber: String, payAmount: BigDecimal, policeNumber: String) {
        require(companies!!.contains(caller.sender)) { "NOT_INSURANCE_COMPANY" }
        require(insurances[documentNumber].insurerAddress == caller.sender) { "WRONG_INSURANCE_COMPANY" }

        insurances[documentNumber] = insurances[documentNumber].copy(
                policeNumber = policeNumber,
                payAmount = payAmount,
                status = WAIT_CLIENT
        )
    }

    override fun clientAcceptsInsuranceAgreement(clientId: UUID, documentNumber: String) {
        require(this.operator!! == caller.sender) { "NOT_AN_OPERATOR" }

        insurances[documentNumber] = insurances[documentNumber].copy(
                status = ACTIVE
        )

        val mortgageId = insurances[documentNumber].mortgageDocument.documentNumber
        require(agreements[mortgageId].status == MortgageAgreementStatus.NEW) {
            "INVALID_MORTGAGE_STATUS"
        }

        val key = "${mortgageId}_${clientId}"

        // Create police
        //
        val dateStart = Date(caller.timestamp)
        val dateEnd = DateUtils.addYears(dateStart, 1)

        val police = InsurancePolice(
                dateStart = dateStart,
                dateEnd = dateEnd,
                insuranceAgreement = insurances[documentNumber],
                policeNumber = insurances[documentNumber].policeNumber,
                id = caller.id,
                status = PoliceStatus.WAIT_FOR_PAYMENT
        )

        polices[police.id] = police
        if (!policeIndex.has(mortgageId)) {
            policeIndex[mortgageId] = mutableListOf()
        }

        policeIndex[mortgageId].add(police.id)


        // Auto advance mortgage agreement when all required insurance agreements
        // are accepted by client
        //
        if (insuranceIndex[key].map {
            insurances[it]
        }.all {
            it.status == ACTIVE
        }) {
            agreements[mortgageId] = agreements[mortgageId].copy(
                    status = WAIT_FOR_PAYMENT
            )
        }
    }

    override fun bankAcceptsPayment(documentNumber: String) {
        require(this.banks!!.contains(caller.sender)) { "NOT_BANK" }
        require(agreements[documentNumber].status == WAIT_FOR_PAYMENT) {
            "INVALID_MORTGAGE_STATUS"
        }
        require(agreements[documentNumber].bankAddress == caller.sender) { "WRONG_BANK" }
        agreements[documentNumber] = agreements[documentNumber].copy(
                status = MortgageAgreementStatus.ACTIVE
        )
    }

    override fun companyAcceptsPayment(documentNumber: String) {
        require(companies!!.contains(caller.sender)) { "NOT_INSURANCE_COMPANY" }
        require(agreements[documentNumber].status == MortgageAgreementStatus.ACTIVE) {
            "INVALID_MORTGAGE_STATUS"
        }

        policeIndex[documentNumber].map {
            polices[it]
        }.forEach {
            polices[it.id] = polices[it.id].copy(
                    status = PoliceStatus.ACTIVE
            )
        }
    }
}
