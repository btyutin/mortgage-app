package com.wavesplatform.we.app.mortgage.controller

import com.wavesplatform.we.app.mortgage.api.TxDto
import com.wavesplatform.we.app.mortgage.service.MortgageContractService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("owner")
class OwnerController(
    private val mortgageContractService: MortgageContractService
) {

    @PostMapping("create")
    fun create(): TxDto {
        return TxDto(mortgageContractService.create())
    }
}