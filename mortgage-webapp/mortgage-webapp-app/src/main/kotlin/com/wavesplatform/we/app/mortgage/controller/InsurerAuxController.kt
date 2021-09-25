package com.wavesplatform.we.app.mortgage.controller

import com.wavesplatform.we.app.mortgage.api.TxDto
import com.wavesplatform.we.app.mortgage.api.requests.RiskFormulaRequest
import com.wavesplatform.we.app.mortgage.service.MortgageContractService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("insurer/aux/")
class InsurerAuxController(
    private val mortgageContractService: MortgageContractService
) {

    @PostMapping("formula")
    fun create(@RequestBody rq: RiskFormulaRequest): TxDto {
        return TxDto(mortgageContractService.setRiskFormula(rq.risk, rq.formula))
    }
}