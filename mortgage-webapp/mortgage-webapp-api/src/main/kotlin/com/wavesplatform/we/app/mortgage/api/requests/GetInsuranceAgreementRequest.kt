package com.wavesplatform.we.app.mortgage.api.requests

import com.wavesplatform.we.app.mortgage.domain.InsuranceAgreementStatus

class GetInsuranceAgreementRequest(
    val status: InsuranceAgreementStatus?
)