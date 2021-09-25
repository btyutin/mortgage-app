package com.wavesplatform.we.app.mortgage.domain

import java.math.BigDecimal
import java.util.Date
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.Id

@Entity
data class MortgageAgreement(
        @Id
        val documentNumber: String,
        val personName: String,
        val personSurname: String,
        val personPatronymic: String,
        val personId: UUID,
        @Enumerated(STRING)
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