package com.wavesplatform.we.app.mortgage.repository

import com.wavesplatform.we.app.mortgage.domain.InsurancePolice
import org.springframework.data.jpa.repository.JpaRepository

interface InsurancePoliceRepository : JpaRepository<InsurancePolice, String>