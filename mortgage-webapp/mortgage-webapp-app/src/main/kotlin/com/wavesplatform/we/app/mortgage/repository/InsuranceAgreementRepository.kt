package com.wavesplatform.we.app.mortgage.repository

import com.wavesplatform.we.app.mortgage.domain.InsuranceAgreement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface InsuranceAgreementRepository : JpaRepository<InsuranceAgreement, String>,
    JpaSpecificationExecutor<InsuranceAgreement>