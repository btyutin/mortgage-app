package com.wavesplatform.we.app.mortgage.controller

import com.wavesplatform.vst.security.commons.OAuth2TokenSupport
import com.wavesplatform.we.app.mortgage.api.TxDto
import com.wavesplatform.we.app.mortgage.api.requests.AcceptByCompanyRequest
import com.wavesplatform.we.app.mortgage.api.requests.InsuranceAgreementApiDto
import com.wavesplatform.we.app.mortgage.mapper.toApiDto
import com.wavesplatform.we.app.mortgage.service.InsuranceAgreementService
import com.wavesplatform.we.app.mortgage.service.MortgageContractService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/insurance/agreements")
class insuranceController(
    private val insuranceAgreementService: InsuranceAgreementService,
    private val vstOAuth2TokenSupport: OAuth2TokenSupport,
    private val mortgageContractService: MortgageContractService
) {
    @GetMapping
    fun list(): List<InsuranceAgreementApiDto> {
        return insuranceAgreementService.list().map { it.toApiDto() }
    }

    @PostMapping("/{id}/acceptByCompany")
    fun acceptByCompany(@PathVariable id: String, @RequestBody rq: AcceptByCompanyRequest): TxDto {
        return TxDto(mortgageContractService.companyAcceptsInsuranceAgreement(id, rq.payAmount, rq.policeNumber))
    }

    @PostMapping("/{id}/acceptByUser")
    fun acceptByUser(@PathVariable id: String): TxDto {
        val personId = vstOAuth2TokenSupport.currentUserPersonInfo.personId
        return TxDto(mortgageContractService.clientAcceptsInsuranceAgreement(personId, id))
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String) = insuranceAgreementService.getOne(id).toApiDto()
}