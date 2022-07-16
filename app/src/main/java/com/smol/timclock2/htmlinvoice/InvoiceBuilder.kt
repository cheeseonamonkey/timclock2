package com.smol.timclock2.htmlinvoice

    /*
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.get
    */
import kotlinx.html.*
import kotlinx.html.dom.append
import org.w3c.dom.Node
import kotlinx.datetime.*


//
//typealiases:
typealias DateTime = kotlinx.datetime.LocalDateTime
typealias Date = kotlinx.datetime.LocalDate
//no localtime
//

//
//extensionS (no typealias here):
fun LocalDateTime.Companion.now() =
    LocalDateTime.parse(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toString())

fun LocalDate.formatted() : String =
    "${this.month.name} ${this.dayOfMonth}, ${this.year}"

fun String.logit() = println(this)
fun Collection<String>.logit() = this.forEach {  println(it) }

fun Double.formatUSD() {
    //this.
}
//
//









class InvoiceBuilder {



    companion object {

        //
        //vars:

        //today's date
        val todayDate =
            DateTime.now().date
        val dateRange =
            DateRange(Date(2022,7,15), Date(2022,7,31))

        //client address
        val clientStr =
            "Enwest Marketing\n2501 Wall Avenue\nOgden, UT 84401"
        val clientStrArr =
            clientStr.split("\n")

        //amount table
        val hourlyRate =
            17.50
        val hoursWorked =
            23.25
        val subtotal =
            hourlyRate * hoursWorked
        val totalDue =
            subtotal


        val meStr =
            "Alexander Huso\n1270 E. South Weber Dr.\nSouth Weber, UT 84405"
        val meStrArr =
            meStr.split("\n")


    }
}