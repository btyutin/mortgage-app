package com.wavesplatform.we.app.mortgage.mapper

import com.wavesplatform.we.app.mortgage.api.requests.InsuranceAgreementApiDto
import com.wavesplatform.we.app.mortgage.domain.InsuranceAgreement

fun InsuranceAgreement.toApiDto() = InsuranceAgreementApiDto(
    documentNumber = documentNumber,
    personName = personName,
    personSurname = personSurname,
    personPatronymic = personPatronymic,
    personId = personId,
    documentDate = documentDate,
    amount = amount,
    mortgageDocument = mortgageDocument,
    risk = risk,
    payAmount = payAmount,
    payDate = payDate,
    status = status,
    policeNumber = policeNumber
)
