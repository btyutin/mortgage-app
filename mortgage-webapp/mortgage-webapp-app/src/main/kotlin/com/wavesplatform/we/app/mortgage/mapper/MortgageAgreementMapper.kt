package com.wavesplatform.we.app.mortgage.mapper

import com.wavesplatform.we.app.mortgage.api.MortgageAgreementApiDto
import com.wavesplatform.we.app.mortgage.domain.MortgageAgreement

fun MortgageAgreement.toApiDto() = MortgageAgreementApiDto(
    documentNumber = documentNumber,
    personName = personName,
    personSurname = personSurname,
    personPatronymic = personPatronymic,
    personId = personId,
    personSex = personSex,
    personBirthDate = personBirthDate,
    personOccupation = personOccupation,
    documentDate = documentDate,
    passportNumber = passportNumber,
    passportSeries = passportSeries,
    type = type,
    city = city,
    estimatedCost = estimatedCost,
    remainingAmount = remainingAmount,
    buildingYear = buildingYear,
    kadasterNumber = kadasterNumber,
    address = address,
    status = status,
    bankAddress = bankAddress
)