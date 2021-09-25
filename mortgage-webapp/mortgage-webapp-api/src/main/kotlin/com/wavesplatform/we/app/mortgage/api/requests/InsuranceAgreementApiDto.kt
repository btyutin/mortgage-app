package com.wavesplatform.we.app.mortgage.api.requests

import com.wavesplatform.we.app.mortgage.domain.InsuranceAgreementStatus
import com.wavesplatform.we.app.mortgage.domain.InsuranceRisk
import com.wavesplatform.we.app.mortgage.domain.MortgageAgreement
import java.math.BigDecimal
import java.util.Date
import java.util.UUID

class InsuranceAgreementApiDto(
    val documentNumber: String,
    val personName: String,
    val personSurname: String,
    val personPatronymic: String,
    val personId: UUID,
    val documentDate: Date,
    val amount: BigDecimal,
    val mortgageDocument: MortgageAgreement,
    val risk: InsuranceRisk,
    val payAmount: BigDecimal,
    val payDate: BigDecimal,
    val status: InsuranceAgreementStatus,
    val policeNumber: String
)