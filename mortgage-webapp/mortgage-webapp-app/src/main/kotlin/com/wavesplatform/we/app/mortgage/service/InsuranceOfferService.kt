package com.wavesplatform.we.app.mortgage.service

import com.wavesplatform.we.app.mortgage.domain.InsuranceOffer
import com.wavesplatform.we.app.mortgage.repository.InsuranceOfferRepository
import org.springframework.stereotype.Service


@Service
class InsuranceOfferService(
    private val insuranceOfferRepository: InsuranceOfferRepository
) {
    fun list(): List<InsuranceOffer> {
        return insuranceOfferRepository.findAll()
    }

    fun getOne(id: String): InsuranceOffer {
        return insuranceOfferRepository.findById(id).get()
    }
}