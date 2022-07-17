package com.smol.timclock2.htmlinvoice

/*
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.get
*/

import com.google.gson.Gson
import kotlinx.html.*
import kotlinx.datetime.*
import kotlinx.html.stream.createHTML
import org.w3c.dom.Attr
import java.io.File
import java.nio.file.attribute.FileAttribute


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
        private val todayDate =
            DateTime.now().date
        private var dateRange =
            DateRange(Date(2022,7,15), Date(2022,7,31))

        //client address
        private var clientStr =
            "Enwest Marketing\n2501 Wall Avenue\nOgden, UT 84401"
        private val clientStrArr =
            clientStr.split("\n")

        //amount table
        private var hourlyRate =
            17.50
        private var hoursWorked =
            23.25
        private val subtotal =
            hourlyRate * hoursWorked
        private val totalDue =
            subtotal


        private var meStr =
            "Alexander Huso\n1270 E. South Weber Dr.\nSouth Weber, UT 84405"
        private val meStrArr =
            meStr.split("\n")


        fun setDateRange(valIn :DateRange) :InvoiceBuilder.Companion {
            dateRange = valIn
            return this
        }
        fun setClientStr(valIn :String) :InvoiceBuilder.Companion {
            clientStr = valIn
            return this
        }
        fun setHourlyRate(valIn :Double) :InvoiceBuilder.Companion {
            hourlyRate = valIn
            return this
        }
        fun setHoursWorked(valIn :Double) :InvoiceBuilder.Companion {
            hoursWorked = valIn
            return this
        }
        fun setMeStr(valIn :String) :InvoiceBuilder.Companion {
            meStr = valIn
            return this
        }



        fun invoiceHtml() {

            val doc =
                createHTML()
                    .html {
                        body {

                            div("holder") {

                                //todayDate
                                h2("centered") {
                                    style = "padding-top: 0.25in;"
                                    +todayDate.formatted()
                                }

                                //client address
                                p("address") {
                                    p {
                                        for (i in clientStrArr)
                                            unsafe { +"${i}<br></br>" }
                                    }
                                }


                                //__________________________________________________________________
                                hr()

                                //fees expenses for...
                                p {
                                    i {
                                        span {
                                            style = "margin-top:25px;"
                                            +"Contractor fees & expenses for:   "
                                        }
                                    }
                                    b {
                                        style = "font-size: 110%; text-decoration: underline;"
                                        +dateRange.toString()
                                    }
                                }


                                //amounts table
                                table("invoiceTable") {


                                    //hourly rate
                                    tr {
                                        td { +"Hourly rate:" }
                                        td { +"$ $hourlyRate" }
                                    }
                                    //hours worked
                                    tr {
                                        td { +"Hours worked:" }
                                        td { +"x $hoursWorked" }
                                    }


                                    //subtotal
                                    tr {
                                        td { +"Subtotal:" }
                                        td { +"$ $subtotal" }
                                    }

                                    //lined empty row

                                    tr {
                                        td { unsafe { +"<hr></hr>" } }
                                        td { unsafe { +"<hr></hr>" } }

                                        //unsafe{+"<hr></hr>"}
                                    }


                                    //total due
                                    tr {
                                        td { +"Total due:" }
                                        td { +"$ $totalDue" }
                                    }

                                }//end invoiceTable table


                                span {
                                    +"Payable to:"

                                }
                                br()

                                p("address") {
                                    style = "box-shadow: 1px 1px 2px 2px rgba(0,0,25,0.05); margin: 17px;"
                                    for (i in meStrArr)
                                        unsafe { +"${i}<br></br>" }
                                }


                                hr()



                                table("hourTableSimple") {

                                    for (i in 1..9) {
                                        tr {
                                            td { i { +"7/12/22" } }
                                            td { +" " }
                                        }
                                        tr {
                                            td { +" " }
                                            td { b { +"x 2.50 hr." } }
                                        }

                                    }

                                    tr {
                                        td { unsafe { +"<hr></hr>" } }
                                        td { unsafe { +"<hr></hr>" } }
                                    }
                                    tr {
                                        td { i { +"Total: " } }
                                        td { +" " }
                                    }
                                    tr {
                                        td { +" " }
                                        td { b { +"x $hoursWorked" } }
                                    }
                                }


                            }//end holder div


                        }
                    }

            //doc.logit()

            val f = File.createTempFile("invoiceHtml", "html").apply { writeText(doc) }

            for(ff in f.readLines())
                ff.logit()


//
//
//you are here!
            //smooth sailing, just made the builder methods for all these vars
            //consider the best way to use data structure from app
            //
//
//






        }//end fun invoiceHtml()
    }//end companion



}//end class
