package com.smol.timclock2.htmlinvoice

import kotlinx.datetime.minus
//important:
import com.smol.timclock2.htmlinvoice.Date
import com.smol.timclock2.htmlinvoice.DateTime


class DateRange(
    val fromDate : Date,
    val toDate : Date
) {

    val numberOfDays = toDate.minus(fromDate)

    override fun toString() = "${fromDate} — ${toDate}"

}