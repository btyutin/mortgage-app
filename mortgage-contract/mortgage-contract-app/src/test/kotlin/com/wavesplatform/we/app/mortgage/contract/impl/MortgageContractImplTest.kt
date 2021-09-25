package com.wavesplatform.we.app.mortgage.contract.impl

import com.wavesplatform.vst.contract.data.ContractCall
import com.wavesplatform.vst.contract.data.Transaction
import com.wavesplatform.vst.contract.data.TransactionBuilder
import com.wavesplatform.vst.contract.state.InMemoryContractState
import com.wavesplatform.we.app.mortgage.contract.MortgageContract
import com.wavesplatform.we.app.mortgage.contract.OfferAccept
import com.wavesplatform.we.app.mortgage.domain.InsuranceRisk.LIFE
import com.wavesplatform.we.app.mortgage.domain.InsuranceRisk.PROPERTY
import com.wavesplatform.we.app.mortgage.domain.InsuranceRisk.TITUL
import com.wavesplatform.we.app.mortgage.domain.MortgageAgreement
import com.wavesplatform.we.app.mortgage.domain.MortgageAgreementStatus.NEW
import com.wavesplatform.we.app.mortgage.domain.PersonSex
import com.wavesplatform.we.app.mortgage.domain.PersonSex.MALE
import com.wavesplatform.we.app.mortgage.domain.formula.BigDecimalRange
import com.wavesplatform.we.app.mortgage.domain.formula.PropertyFormula
import com.wavesplatform.we.app.mortgage.domain.formula.RangeWithCoefficient
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.Date
import java.util.UUID

class MortgageContractImplTest {

    private val tx: Transaction = TransactionBuilder().setType(103).build()
    private val state = InMemoryContractState(tx, mutableMapOf())
    private val personId = UUID.randomUUID()

    @Test
    fun `test whole process`() {
        // Create contract
        //
        asUser("OWNER") {
            it.create(
                    banks = listOf("BANK_1", "BANK_2"),
                    companies = listOf("IC_1", "IC_2")
            )
        }

        // Set approved ICs by banks
        //
        asUser("BANK_1") {
            it.setInsuranceCompanyForBank(
                    companies = listOf("IC_1")
            )
        }

        asUser("BANK_2") {
            it.setInsuranceCompanyForBank(
                    companies = listOf("IC_2")
            )
        }

        // Enter formulas
        //
        asUser("IC_1") {
            val formula = PropertyFormula(
                    bankMap = mapOf(
                            "BANK_1" to 0.1.toBigDecimal(),
                            "BANK_2" to 0.2.toBigDecimal()
                    ),
                    ageMap = listOf(
                            RangeWithCoefficient(
                                    range = BigDecimalRange(
                                            from = BigDecimal.ZERO,
                                            to = 30.toBigDecimal()
                                    ),
                                    koeff = 0.1.toBigDecimal()
                            ),
                            RangeWithCoefficient(
                                    range = BigDecimalRange(
                                            from = 30.toBigDecimal(),
                                            to = 90.toBigDecimal()
                                    ),
                                    koeff = 0.2.toBigDecimal()
                            )
                    ),
                    remainMap = listOf(
                            RangeWithCoefficient(
                                    range = BigDecimalRange(
                                            from = BigDecimal.ZERO,
                                            to = Integer.MAX_VALUE.toBigDecimal()
                                    ),
                                    koeff = 0.1.toBigDecimal()
                            )
                    ),
                    typeMap = mapOf(
                            "Дом" to 0.1.toBigDecimal(),
                            "Квартира" to 0.2.toBigDecimal()
                    ),
                    sexMap = mapOf(
                            PersonSex.FEEMALE to 0.1.toBigDecimal(),
                            PersonSex.MALE to 0.2.toBigDecimal()
                    )
            )
            it.setRiskFormula(formula, PROPERTY)
            it.setRiskFormula(formula, LIFE)
            it.setRiskFormula(formula, TITUL)
        }

        asUser("IC_2") {
            val formula = PropertyFormula(
                    bankMap = mapOf(
                            "BANK_1" to 0.11.toBigDecimal(),
                            "BANK_2" to 0.22.toBigDecimal()
                    ),
                    ageMap = listOf(
                            RangeWithCoefficient(
                                    range = BigDecimalRange(
                                            from = BigDecimal.ZERO,
                                            to = 30.toBigDecimal()
                                    ),
                                    koeff = 0.11.toBigDecimal()
                            ),
                            RangeWithCoefficient(
                                    range = BigDecimalRange(
                                            from = 30.toBigDecimal(),
                                            to = 90.toBigDecimal()
                                    ),
                                    koeff = 0.21.toBigDecimal()
                            )
                    ),
                    remainMap = listOf(
                            RangeWithCoefficient(
                                    range = BigDecimalRange(
                                            from = BigDecimal.ZERO,
                                            to = Integer.MAX_VALUE.toBigDecimal()
                                    ),
                                    koeff = 0.12.toBigDecimal()
                            )
                    ),
                    typeMap = mapOf(
                            "Дом" to 0.12.toBigDecimal(),
                            "Квартира" to 0.21.toBigDecimal()
                    ),
                    sexMap = mapOf(
                            PersonSex.FEEMALE to 0.11.toBigDecimal(),
                            PersonSex.MALE to 0.22.toBigDecimal()
                    )
            )
            it.setRiskFormula(formula, PROPERTY)
            it.setRiskFormula(formula, LIFE)
            it.setRiskFormula(formula, TITUL)
        }

        asUser("BANK_1", "1") {
            it.createMortgageAgreement(MortgageAgreement(
                    address = "Адрес",
                    bankAddress = "BANK_1",
                    buildingYear = 1999,
                    city = "Москва",
                    documentDate = Date(),
                    documentNumber = "1/111",
                    estimatedCost = BigDecimal("1000000"),
                    kadasterNumber = "some",
                    passportNumber = "",
                    passportSeries = "",
                    personSurname = "",
                    personName = "",
                    personPatronymic = "",
                    personBirthDate = Date(),
                    personOccupation = "",
                    personSex = MALE,
                    personId = personId,
                    remainingAmount = BigDecimal("100000"),
                    type = "Дом",
                    status = NEW
            ))
        }

        asUser("OWNER", "2") {
            it.clientAcceptOffer(
                    offers = listOf(
                            OfferAccept(PROPERTY, sha256("1_IC_1_PROPERTY")),
                            OfferAccept(TITUL, sha256("1_IC_1_TITUL"))
                    )
            )
        }

        asUser("IC_1") {
            it.companyAcceptsInsuranceAgreement(
                    documentNumber = sha256("${sha256("1_IC_1_PROPERTY")}_2"),
                    payAmount = BigDecimal("3000"),
                    policeNumber = "P/11"
            )

            it.companyAcceptsInsuranceAgreement(
                    documentNumber = sha256("${sha256("1_IC_1_TITUL")}_2"),
                    payAmount = BigDecimal("2000"),
                    policeNumber = "P/12"
            )
        }

        asUser("OWNER") {
            it.clientAcceptsInsuranceAgreement(
                    clientId = personId,
                    documentNumber = sha256("${sha256("1_IC_1_PROPERTY")}_2")
            )
            it.clientAcceptsInsuranceAgreement(
                    clientId = personId,
                    documentNumber = sha256("${sha256("1_IC_1_TITUL")}_2")
            )
        }

        asUser("BANK_1") {
            it.bankAcceptsPayment("1/111")
        }

        asUser("IC_1") {
            it.companyAcceptsPayment("1/111")
        }

    }

    private fun asUser(address: String, txId: String = "", action: (MortgageContract) -> Unit) {
        val tx = TransactionBuilder()
                .setSender(address)
                .setId(txId)
                .setTimestamp(Date().time)
                .setType(104)
                .build()
        val call = ContractCall(tx)
        val contract = MortgageContractImpl(state, call)
        action(contract)
    }
}
