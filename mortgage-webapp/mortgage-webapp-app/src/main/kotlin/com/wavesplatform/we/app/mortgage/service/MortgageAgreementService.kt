package com.wavesplatform.we.app.mortgage.service

import com.wavesplatform.vst.api.identity.VstPersonApi
import com.wavesplatform.we.app.mortgage.api.requests.CreateMortgageAgreementRequest
import com.wavesplatform.we.app.mortgage.domain.MortgageAgreement
import com.wavesplatform.we.app.mortgage.domain.MortgageAgreementStatus
import com.wavesplatform.we.app.mortgage.repository.MortgageAgreementRepository
import org.springframework.http.HttpStatus.OK
import org.springframework.stereotype.Service

@Service
class MortgageAgreementService(
    private val mortgageAgreementRepository: MortgageAgreementRepository,
    private val mortgageContractService: MortgageContractService,
    private val vstPersonApi: VstPersonApi
) {
    fun create(agreement: CreateMortgageAgreementRequest, bankAddress: String): String {
        val person = vstPersonApi.getOne(agreement.personId)
        require(person.statusCode == OK) { "NO_SUCH_PERSON" }
        require(person.body != null) { "NO_SUCH_PERSON" }
        require(person.body!!.businessRoles.contains("BORROWER")) { "REFERENCE_TO_INVALID_PERSON_TYPE" }


        val result = MortgageAgreement(
                documentNumber = agreement.documentNumber,
                personName = agreement.personName,
                personSurname = agreement.personSurname,
                personPatronymic = agreement.personPatronymic,
                personId = agreement.personId,
                personSex = agreement.personSex,
                personBirthDate = agreement.personBirthDate,
                personOccupation = agreement.personOccupation,
                documentDate = agreement.documentDate,
                passportNumber = agreement.passportNumber,
                passportSeries = agreement.passportSeries,
                type = agreement.type.code,
                city = agreement.city,
                estimatedCost = agreement.estimatedCost,
                remainingAmount = agreement.remainingAmount,
                buildingYear = agreement.buildingYear,
                kadasterNumber = agreement.kadasterNumber,
                address = agreement.address,
                status = MortgageAgreementStatus.NEW,
                bankAddress = bankAddress
            )

        return mortgageContractService.createMortgageAgreement(result)

    }

    fun list(): List<MortgageAgreement> {
        return mortgageAgreementRepository.findAll()
    }

    fun getOne(id: String): MortgageAgreement? {
        return mortgageAgreementRepository.findById(id).get()
    }
}