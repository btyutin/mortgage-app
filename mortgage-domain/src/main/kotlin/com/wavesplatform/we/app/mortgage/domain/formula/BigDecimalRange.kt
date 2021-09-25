package com.wavesplatform.we.app.mortgage.domain.formula

import java.math.BigDecimal

data class BigDecimalRange(
        val from: BigDecimal,
        val to: BigDecimal
) {
    fun isWithin(value: BigDecimal): Boolean {
        return value in from..to
    }

    fun isWithin(value: Long): Boolean {
        return isWithin(value.toBigDecimal())
    }
}