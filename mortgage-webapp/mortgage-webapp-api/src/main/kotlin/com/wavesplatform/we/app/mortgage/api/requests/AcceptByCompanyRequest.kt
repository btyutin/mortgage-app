package com.wavesplatform.we.app.mortgage.api.requests

import java.math.BigDecimal

data class AcceptByCompanyRequest(
        val payAmount: BigDecimal,
        val policeNumber: String
)