package com.wavesplatform.we.app.mortgage.controller

import com.wavesplatform.we.app.mortgage.api.TxStatusDto
import com.wavesplatform.we.app.mortgage.service.MortgageContractService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("tx")
class TxController(
        val mortgageContractService: MortgageContractService
) {

    @GetMapping("/tx/{id}/status")
    fun txStatus(@PathVariable("id") id: String): TxStatusDto {
        return TxStatusDto(mortgageContractService.txStatus(id))
    }

}