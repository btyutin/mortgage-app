package com.wavesplatform.we.app.mortgage.domain.formula

import com.wavesplatform.we.app.mortgage.domain.MortgageAgreement
import com.wavesplatform.we.app.mortgage.domain.PersonSex
import java.math.BigDecimal
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PropertyFormula(
        val bankMap: Map<String, BigDecimal>,
        val remainMap: List<RangeWithCoefficient>,
        val typeMap: Map<String, BigDecimal>,
        val sexMap: Map<PersonSex, BigDecimal>,
        val ageMap: List<RangeWithCoefficient>
): AbstractFormula<MortgageAgreement>() {
    override fun eval(target: MortgageAgreement): BigDecimal {
        val k1 = bankMap[target.bankAddress]
        val k2 = remainMap.find { it.range.isWithin(target.remainingAmount) }?.koeff
        val k3 = typeMap[target.type]
        val k4 = sexMap[target.personSex]
        val age = getDiffYears(target.personBirthDate, Date())
        val k5 = ageMap.find { it.range.isWithin(age.toBigDecimal()) }?.koeff

        return (k1!! + k2!! + k3!! + k4!! + k5!!) *  target.remainingAmount
    }

    private fun getDiffYears(first: Date, last: Date): Int {
        val a: Calendar = getCalendar(first)
        val b: Calendar = getCalendar(last)
        var diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR)
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE)) {
            diff--
        }
        return diff
    }

    private fun getCalendar(date: Date): Calendar {
        val cal = Calendar.getInstance(Locale.US)
        cal.time = date
        return cal
    }
}