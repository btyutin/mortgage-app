package com.wavesplatform.we.app.mortgage.controller

import com.wavesplatform.vst.security.commons.OAuth2TokenSupport
import com.wavesplatform.we.app.mortgage.api.MortgageAgreementApiDto
import com.wavesplatform.we.app.mortgage.api.TxDto
import com.wavesplatform.we.app.mortgage.api.requests.CreateMortgageAgreementRequest
import com.wavesplatform.we.app.mortgage.mapper.toApiDto
import com.wavesplatform.we.app.mortgage.service.MortgageAgreementService
import com.wavesplatform.we.app.mortgage.service.MortgageContractService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("mortgage/agreements/")
class MortgageController(
    private val mortgageAgreementService: MortgageAgreementService,
    private val mortgageContractService: MortgageContractService,
    private val vstOAuth2TokenSupport: OAuth2TokenSupport
) {

    @GetMapping
    fun list(): List<MortgageAgreementApiDto> {
        return mortgageAgreementService.list().map { it.toApiDto() }
    }

    @PostMapping
    fun create(@RequestBody mortgageAgreement: CreateMortgageAgreementRequest): TxDto {
        val address = vstOAuth2TokenSupport.currentUserPersonInfo.participantAddress
        return TxDto(mortgageAgreementService.create(mortgageAgreement, address))
    }

    @PostMapping("{id}/bankAcceptsPayment")
    fun bankAcceptsPayment(@PathVariable("id") id: String): TxDto {
        return TxDto(mortgageContractService.bankAcceptsPayment(id))
    }

    @PostMapping("{id}/companyAcceptsPayment")
    fun companyAcceptsPayment(@PathVariable("id") id: String): TxDto {
        return TxDto(mortgageContractService.companyAcceptsPayment(id))
    }



    @GetMapping("/{id}")
    fun getOne(@PathVariable("id") id: String) =
        mortgageAgreementService.getOne(
            id = id
        )?.toApiDto()
}