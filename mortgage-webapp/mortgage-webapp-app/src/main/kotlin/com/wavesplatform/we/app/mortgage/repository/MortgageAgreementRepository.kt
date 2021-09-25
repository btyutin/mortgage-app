package com.wavesplatform.we.app.mortgage.repository

import com.wavesplatform.we.app.mortgage.domain.MortgageAgreement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface MortgageAgreementRepository : JpaRepository<MortgageAgreement, String>,
    JpaSpecificationExecutor<MortgageAgreement>