package com.wavesplatform.we.app.mortgage.controller

import com.wavesplatform.we.app.mortgage.domain.InsurancePolice
import com.wavesplatform.we.app.mortgage.service.InsurancePoliceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/insurance/polices")
class InsurancePoliceController(
    private val insurancePoliceService: InsurancePoliceService
) {
    @GetMapping
    fun list(): List<InsurancePolice> {
        return insurancePoliceService.list()
    }
}