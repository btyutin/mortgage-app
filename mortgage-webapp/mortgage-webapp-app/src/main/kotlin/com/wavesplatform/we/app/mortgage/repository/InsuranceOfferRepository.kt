package com.wavesplatform.we.app.mortgage.repository

import com.wavesplatform.we.app.mortgage.domain.InsuranceOffer
import org.springframework.data.jpa.repository.JpaRepository

interface InsuranceOfferRepository : JpaRepository<InsuranceOffer, String>