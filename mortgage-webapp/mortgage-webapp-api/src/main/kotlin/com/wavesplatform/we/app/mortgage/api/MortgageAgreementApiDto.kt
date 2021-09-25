package com.wavesplatform.we.app.mortgage.api

import com.wavesplatform.we.app.mortgage.domain.MortgageAgreementStatus
import com.wavesplatform.we.app.mortgage.domain.PersonSex
import java.math.BigDecimal
import java.util.Date
import java.util.UUID

class MortgageAgreementApiDto(
    val documentNumber: String,
    val personName: String,
    val personSurname: String,
    val personPatronymic: String,
    val personId: UUID,
    val personSex: PersonSex,
    val personBirthDate: Date,
    val personOccupation: String,
    val documentDate: Date,
    val passportNumber: String,
    val passportSeries: String,
    val type: String,
    val city: String,
    val estimatedCost: BigDecimal,
    val remainingAmount: BigDecimal,
    val buildingYear: Int,
    val kadasterNumber: String,
    val address: String,
    val status: MortgageAgreementStatus,
    val bankAddress: String
)