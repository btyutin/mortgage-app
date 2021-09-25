package com.wavesplatform.we.app.mortgage.domain

import java.math.BigDecimal
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.OneToOne

@Entity
data class InsuranceOffer(
    @Id
    val id: String,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinTable(name="insurance_offer_mortgate_agreement",
            joinColumns=[JoinColumn(name="mortgage_id")] ,
            inverseJoinColumns=[JoinColumn(name="offer_id")])
    val mortgageDocument: MortgageAgreement,
    val insurerAddress: String,
    val risk: InsuranceRisk,
    val payAmount: BigDecimal,
    @Enumerated(EnumType.STRING)
    val status: InsuranceOfferStatus

)