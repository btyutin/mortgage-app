package com.wavesplatform.we.app.mortgage.contract

import com.wavesplatform.vst.contract.ContractAction
import com.wavesplatform.vst.contract.ContractInit
import com.wavesplatform.vst.contract.InvokeParam
import com.wavesplatform.we.app.mortgage.domain.InsuranceRisk
import com.wavesplatform.we.app.mortgage.domain.MortgageAgreement
import com.wavesplatform.we.app.mortgage.domain.formula.PropertyFormula
import java.math.BigDecimal
import java.util.UUID

interface MortgageContract {

    @ContractInit
    fun create(
            @InvokeParam(name = "banks") banks: List<String>,
            @InvokeParam(name = "companies") companies: List<String>
    )

    @ContractAction
    fun setRiskFormula(
            @InvokeParam(name = "formula") formula: PropertyFormula,
            @InvokeParam(name = "type") type: InsuranceRisk
    )

    @ContractAction
    fun setInsuranceCompanyForBank(
            @InvokeParam(name = "companies") companies: List<String>
    )

    @ContractAction
    fun createMortgageAgreement(
            @InvokeParam(name = "agreement") agreement: MortgageAgreement
    )

    @ContractAction
    fun clientAcceptOffer(
            @InvokeParam(name = "offers") offers: List<OfferAccept>
    )

    @ContractAction
    fun companyAcceptsInsuranceAgreement(
            @InvokeParam(name = "documentNumber") documentNumber: String,
            @InvokeParam(name = "payAmount") payAmount: BigDecimal,
            @InvokeParam(name = "policeNumber") policeNumber: String
    )

    @ContractAction
    fun clientAcceptsInsuranceAgreement(
            @InvokeParam(name = "clientId") clientId: UUID,
            @InvokeParam(name = "documentNumber") documentNumber: String
    )

    @ContractAction
    fun bankAcceptsPayment(
            @InvokeParam(name = "documentNumber") documentNumber: String
    )

    @ContractAction
    fun companyAcceptsPayment(
            @InvokeParam(name = "documentNumber") documentNumber: String
    )

}
