package com.wavesplatform.we.app.mortgage.service

import com.wavesplatform.we.app.mortgage.domain.InsurancePolice
import com.wavesplatform.we.app.mortgage.repository.InsurancePoliceRepository
import org.springframework.stereotype.Service

@Service
class InsurancePoliceService(
        val insurancePoliceRepository: InsurancePoliceRepository
) {
    fun list(): List<InsurancePolice> {
        return insurancePoliceRepository.findAll()
    }
}