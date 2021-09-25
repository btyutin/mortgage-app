package com.wavesplatform.we.app.mortgage.domain

import java.math.BigDecimal
import java.util.Date
import java.util.UUID
import javax.persistence.CascadeType.ALL
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
data class InsuranceAgreement(
        @Id
        val documentNumber: String,
        val personName: String,
        val personSurname: String,
        val personPatronymic: String,
        val personId: UUID,
        val documentDate: Date,
        val amount: BigDecimal,
        val insurerAddress: String,
        @OneToOne(cascade = [ALL])
        @JoinTable(name="insurance_agreement_mortgate_agreement",
                joinColumns=[JoinColumn(name="mortgage_id")] ,
                inverseJoinColumns=[JoinColumn(name="insurance_id")])
        val mortgageDocument: MortgageAgreement,
        @Enumerated(EnumType.STRING)
        val risk: InsuranceRisk,
        val payAmount: BigDecimal,
        val payDate: BigDecimal,
        @Enumerated(EnumType.STRING)
        val status: InsuranceAgreementStatus,
        val policeNumber: String
)