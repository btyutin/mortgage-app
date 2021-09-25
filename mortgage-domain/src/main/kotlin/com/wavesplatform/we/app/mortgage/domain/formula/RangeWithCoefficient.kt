package com.wavesplatform.we.app.mortgage.domain.formula

import java.math.BigDecimal

data class RangeWithCoefficient(
        val range: BigDecimalRange,
        val koeff: BigDecimal
)