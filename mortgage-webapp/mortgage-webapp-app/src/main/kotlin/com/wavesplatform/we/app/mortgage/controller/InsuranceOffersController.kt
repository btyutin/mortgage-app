package com.wavesplatform.we.app.mortgage.controller

import com.wavesplatform.we.app.mortgage.api.TxDto
import com.wavesplatform.we.app.mortgage.contract.OfferAccept
import com.wavesplatform.we.app.mortgage.domain.InsuranceOffer
import com.wavesplatform.we.app.mortgage.service.InsuranceOfferService
import com.wavesplatform.we.app.mortgage.service.MortgageContractService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/insurance/offers")
class InsuranceOffersController(
    private val insuranceOfferService: InsuranceOfferService,
    private val mortgageContractService: MortgageContractService
) {
    @GetMapping
    fun list(): List<InsuranceOffer> {
        return insuranceOfferService.list()
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String) = insuranceOfferService.getOne(id)

    @PostMapping("/accept")
    fun acceptOffers(@RequestBody offerAcceptList: List<OfferAccept>): TxDto {
        return TxDto(mortgageContractService.clientAcceptOffer(offerAcceptList))
    }
}