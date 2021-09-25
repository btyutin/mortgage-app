package com.wavesplatform.we.app.mortgage.domain

import java.util.Date
import javax.persistence.CascadeType.ALL
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToOne
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter.All

@Entity
data class InsurancePolice(
        @Id
        val id: String,
        @ManyToOne(cascade = [ALL])
        @JoinTable(name="insurance_police_insurance_agreement",
                joinColumns=[JoinColumn(name="police_id")] ,
                inverseJoinColumns=[JoinColumn(name="insurance_id")])
        val insuranceAgreement: InsuranceAgreement,
        val policeNumber: String,
        val dateStart: Date,
        val dateEnd: Date,
        @Enumerated(STRING)
        val status: PoliceStatus
)