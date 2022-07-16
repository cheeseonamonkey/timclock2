package com.smol.timclock2.classes


import android.util.Log
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit.MINUTES

fun String.logit() = Log.d("logit", this)


typealias Date = LocalDate
typealias Time = LocalTime
typealias DateTime = LocalDateTime

fun Long.minutesToHoursMinutesString(): String {
    if(this >= 60) {
        val hrs = this / 60
        val mins = this % 60
        return "${hrs.toString()}hrs ${mins.toString()}mns"
    }else
        return "${this} mins"

}


fun Time?.formatted(): String = this?.format(DateTimeFormatter.ofPattern("h:mm a")) ?: "(NOT:SET)"
fun Date?.formatted(): String = this?.format(DateTimeFormatter.ofPattern("MM/d/yyyy")) ?: "NOT/SET"
fun DateTime?.formatted() :String = "${this?.toLocalDate().formatted()}  (${this?.toLocalTime().formatted()})"



//
class Entry (
    val dateTime : DateTime
){

    val date: Date = dateTime.toLocalDate()
    val time: Time = dateTime.toLocalTime()
    override fun toString() =
        "ENTRY(${dateTime.formatted()})"

    companion object {

    }

}




data class EntrySet(

    var inEntry : Entry = Entry(DateTime.now()),
    var outEntry : Entry? = null,
    var tasks : MutableList<String> = mutableListOf(),
    var notes : String = "",
    var trelloLinks : MutableList<String> = mutableListOf()

) {

    val isOpen : Boolean = (outEntry == null)

    val duration : Long
        get() {
            if(outEntry == null)
                return 0
            else
                return MINUTES.between(
                    inEntry?.time,
                    outEntry?.time)
        }

    val date
        get() = inEntry?.date



    fun isOpenEntry() : Boolean = (outEntry == null)



    fun setIn( ent : Entry) :EntrySet {
        inEntry = ent
        "set InEntry: ${inEntry.toString()}".logit()
        return this
    }
    fun setOut( ent : Entry) :EntrySet {
        outEntry = ent
        "set OutEntry: ${outEntry.toString()}".logit()
        return this
    }

    fun setIn( entryTime :DateTime) :EntrySet =
        setIn(Entry(entryTime))
    fun setOut( entryTime :DateTime) :EntrySet =
        setOut(Entry(entryTime))

    fun setIn ( entryTime: Time) :EntrySet =
        setIn(DateTime.of(date ?: Date.now() ,entryTime))
fun setOut ( entryTime: Time) :EntrySet =
    setOut(DateTime.of(date ?: Date.now() ,entryTime))


    fun setInToNow() :EntrySet {
        inEntry = Entry(DateTime.now())
        return this
    }
    fun setOutToNow() :EntrySet {
        outEntry = Entry(DateTime.now())
        return this
    }

    override fun toString(): String {
        return "EntrySet:\n" +
                "\t${date.formatted()} \t[${inEntry?.time?.formatted()} - ${outEntry?.time?.formatted()}] ($duration minutes)" +
                "\t\t{${tasks}, ${notes}, ${trelloLinks ?: ""}}"
    }


    companion object {

    }
}