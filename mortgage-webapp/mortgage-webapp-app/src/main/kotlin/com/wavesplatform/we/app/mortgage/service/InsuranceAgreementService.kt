package com.wavesplatform.we.app.mortgage.service

import com.wavesplatform.we.app.mortgage.api.requests.GetInsuranceAgreementRequest
import com.wavesplatform.we.app.mortgage.domain.InsuranceAgreement
import com.wavesplatform.we.app.mortgage.repository.InsuranceAgreementRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service


@Service
class InsuranceAgreementService(
    private val insuranceAgreementRepository: InsuranceAgreementRepository
) {
    fun list(): List<InsuranceAgreement> {
        return insuranceAgreementRepository.findAll()
    }

    fun getOne(id: String): InsuranceAgreement {
        return insuranceAgreementRepository.findById(id).get()
    }
}