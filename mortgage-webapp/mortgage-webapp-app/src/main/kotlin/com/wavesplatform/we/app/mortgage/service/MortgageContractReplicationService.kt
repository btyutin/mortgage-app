package com.wavesplatform.we.app.mortgage.service

import com.wavesplatform.vst.tx.observer.annotation.VstBlockListener
import com.wavesplatform.vst.tx.observer.annotation.VstKeyFilter
import com.wavesplatform.vst.tx.observer.api.model.VstKeyEvent
import com.wavesplatform.we.app.mortgage.domain.InsuranceAgreement
import com.wavesplatform.we.app.mortgage.domain.InsuranceOffer
import com.wavesplatform.we.app.mortgage.domain.InsurancePolice
import com.wavesplatform.we.app.mortgage.domain.MortgageAgreement
import com.wavesplatform.we.app.mortgage.repository.InsuranceAgreementRepository
import com.wavesplatform.we.app.mortgage.repository.InsuranceOfferRepository
import com.wavesplatform.we.app.mortgage.repository.InsurancePoliceRepository
import com.wavesplatform.we.app.mortgage.repository.MortgageAgreementRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MortgageContractReplicationService(
        val mortgageAgreementRepository: MortgageAgreementRepository,
        val insuranceOfferRepository: InsuranceOfferRepository,
        val insuranceAgreementRepository: InsuranceAgreementRepository,
        val insurancePoliceRepository: InsurancePoliceRepository
) {

        @VstBlockListener
        fun replicateMortgageAgreement(@VstKeyFilter(keyPrefix = "AGREEMENTS_") e: VstKeyEvent<MortgageAgreement>) {
                mortgageAgreementRepository.save(e.payload)
        }

        @VstBlockListener
        fun replicateInsuranceOffer(@VstKeyFilter(keyPrefix = "OFFERS_") e: VstKeyEvent<InsuranceOffer>) {
                val actualAgreement = mortgageAgreementRepository.findById(
                        e.payload.mortgageDocument.documentNumber
                ).orElse(e.payload.mortgageDocument)
                insuranceOfferRepository.save(e.payload.copy(
                        mortgageDocument = actualAgreement
                ))
        }

        @VstBlockListener
        fun replicateInsuranceAgreement(@VstKeyFilter(keyPrefix = "INSURANCES_") e: VstKeyEvent<InsuranceAgreement>) {
                val actualAgreement = mortgageAgreementRepository.findById(
                        e.payload.mortgageDocument.documentNumber
                ).orElse(e.payload.mortgageDocument)
                insuranceAgreementRepository.save(e.payload.copy(
                        mortgageDocument = actualAgreement
                ))
        }

        @VstBlockListener
        fun replicateInsurancePolice(@VstKeyFilter(keyPrefix = "POLICES_") e: VstKeyEvent<InsurancePolice>) {
                val actualAgreement = insuranceAgreementRepository.findById(
                        e.payload.insuranceAgreement.documentNumber
                ).orElse(e.payload.insuranceAgreement)
                insurancePoliceRepository.save(e.payload.copy(
                        insuranceAgreement = actualAgreement
                ))
        }
}
