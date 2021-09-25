package com.wavesplatform.we.app.mortgage.service

import com.wavesplatform.vst.api.identity.VstCompanyApi
import com.wavesplatform.vst.api.identity.VstPersonApi
import com.wavesplatform.vst.api.identity.model.PersonSearchParams
import com.wavesplatform.vst.contract.factory.ContractClientFactory
import com.wavesplatform.vst.node.WeNodeApi
import com.wavesplatform.vst.node.dto.TxStatus.ERROR
import com.wavesplatform.vst.node.dto.TxStatus.FAILURE
import com.wavesplatform.vst.node.dto.TxStatus.SUCCESS
import com.wavesplatform.we.app.mortgage.api.TxStatus
import com.wavesplatform.we.app.mortgage.api.TxStatus.COMPLETE
import com.wavesplatform.we.app.mortgage.api.TxStatus.PENDING
import com.wavesplatform.we.app.mortgage.api.TxStatus.UNKNOWN
import com.wavesplatform.we.app.mortgage.contract.MortgageContract
import com.wavesplatform.we.app.mortgage.contract.OfferAccept
import com.wavesplatform.we.app.mortgage.domain.InsuranceRisk
import com.wavesplatform.we.app.mortgage.domain.MortgageAgreement
import com.wavesplatform.we.app.mortgage.domain.formula.PropertyFormula
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.UUID

@Service
class MortgageContractService(
    val factory: ContractClientFactory<MortgageContract>,
    val weNodeApi: WeNodeApi,
    val vstCompanyApi: VstCompanyApi,
    val vstPersonApi: VstPersonApi,
    @Value("\${mortgage.contract:}") val contractId: String
) {
        fun create(): String {
                val banks = collectAddresses("BANK")
                val companies = collectAddresses("INSURER")
                val api = factory.client { it.contractName("mortgage-${UUID.randomUUID()}") }
                api.contract().create(banks, companies)
                return api.lastTxId
        }

        fun createMortgageAgreement(agreement: MortgageAgreement): String {
                val api = factory.client { it.contractId(contractId) }
                api.contract().createMortgageAgreement(agreement)
                return api.lastTxId
        }

        fun setInsuranceCompanyForBank(companies: List<String>): String {
                val api = factory.client { it.contractId(contractId) }
                api.contract().setInsuranceCompanyForBank(companies)
                return api.lastTxId
        }

        fun setRiskFormula(risk: InsuranceRisk, formula: PropertyFormula): String {
                val api = factory.client { it.contractId(contractId) }
                api.contract().setRiskFormula(formula, risk)
                return api.lastTxId
        }

        fun bankAcceptsPayment(documentNumber: String): String {
                val api = factory.client { it.contractId(contractId) }
                api.contract().bankAcceptsPayment(documentNumber)
                return api.lastTxId
        }

        fun companyAcceptsInsuranceAgreement(documentNumber: String, payAmount: BigDecimal, policeNumber: String): String {
                val api = factory.client { it.contractId(contractId) }
                api.contract().companyAcceptsInsuranceAgreement(documentNumber, payAmount, policeNumber)
                return api.lastTxId
        }


        fun clientAcceptOffer(offerAcceptList: List<OfferAccept>): String {
                val api = factory.client { it.contractId(contractId) }
                api.contract().clientAcceptOffer(offerAcceptList)
                return api.lastTxId
        }

        fun clientAcceptsInsuranceAgreement(clientId: UUID, documentNumber: String): String {
                val api = factory.client { it.contractId(contractId) }
                api.contract().clientAcceptsInsuranceAgreement(clientId, documentNumber)
                return api.lastTxId
        }

        fun companyAcceptsPayment(documentNumber: String): String {
                val api = factory.client { it.contractId(contractId) }
                api.contract().companyAcceptsPayment(documentNumber)
                return api.lastTxId
        }

        private fun collectAddresses(type: String): List<String> {
                return vstCompanyApi.all.filter {
                        it.type == type
                }.flatMap {
                        vstPersonApi.getAll(PersonSearchParams.builder().companyId(it.id).build())
                }.map {
                        it.participantAddress
                }.toSet().toList()
        }


    fun txStatus(id: String): TxStatus {
            try {
                    val tx = weNodeApi.transactionInfo(id)
                    if (tx != null) {
                            return COMPLETE
                    }
                    val utx = weNodeApi.unconfirmedTransactionInfo(id)
                    if (utx != null) {
                            return PENDING
                    }
                    val statuses = weNodeApi.getContractTxStatus(id)
                    if (statuses.any { it.status == SUCCESS }) {
                            return COMPLETE
                    }
                    if (statuses.any { it.status == ERROR || it.status == FAILURE }) {
                            return TxStatus.ERROR
                    }
                    return UNKNOWN
            } catch(e: Exception) {
                    return UNKNOWN
            }
    }

}
