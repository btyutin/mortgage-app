package com.wavesplatform.we.app.mortgage.controller

import com.wavesplatform.we.app.mortgage.api.TxDto
import com.wavesplatform.we.app.mortgage.service.MortgageContractService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("bank/aux/")
class BankAuxController(
    private val mortgageContractService: MortgageContractService
) {

    @PostMapping("accredited")
    fun create(@RequestBody companies: List<String>): TxDto {
        return TxDto(mortgageContractService.setInsuranceCompanyForBank(companies))
    }
}