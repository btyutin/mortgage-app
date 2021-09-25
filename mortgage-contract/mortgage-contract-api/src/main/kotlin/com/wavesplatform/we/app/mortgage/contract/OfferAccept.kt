package com.wavesplatform.we.app.mortgage.contract

import com.wavesplatform.we.app.mortgage.domain.InsuranceRisk

data class OfferAccept(
        val risk: InsuranceRisk,
        val offerId: String
)