package com.wavesplatform.we.app.mortgage.api.requests

import com.wavesplatform.we.app.mortgage.domain.InsuranceRisk
import com.wavesplatform.we.app.mortgage.domain.formula.PropertyFormula

data class RiskFormulaRequest(
        val risk: InsuranceRisk,
        val formula: PropertyFormula
)