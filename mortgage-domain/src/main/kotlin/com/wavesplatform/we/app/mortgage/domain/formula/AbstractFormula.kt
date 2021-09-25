package com.wavesplatform.we.app.mortgage.domain.formula

import java.math.BigDecimal

abstract class AbstractFormula<T> {
    abstract fun eval(target: T): BigDecimal
}