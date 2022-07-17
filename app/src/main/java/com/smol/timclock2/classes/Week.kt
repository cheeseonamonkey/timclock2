package com.smol.timclock2.classes

import com.google.gson.Gson
import java.lang.Exception
import java.time.LocalDate
import java.time.LocalDateTime

data class Week(
    val weekStartDate : Date = LocalDate.now(),

) {
    var entrySets : MutableList<EntrySet>

    init {
        entrySets = MutableList<EntrySet>( 0, { i -> EntrySet() })
    }

    val totalMinutes : Long
        get() {
            var minsOut = 0.toLong();
            for (es in entrySets)
                minsOut += es.duration

            return minsOut
        }
    val weekEndDate
        get() = weekStartDate.plusDays(7)

    //get date by day number
    fun getDay(d:Int) =
        weekStartDate.plusDays(d.toLong())

    fun getOpenEntry() : EntrySet {
        for(e in entrySets)
            if(e.isOpenEntry())
                return e


            return entrySets.last()

    }

    fun isEntryOpen() : Boolean {
        for(e in entrySets)
            if(e.isOpenEntry())
                return true

        //else
        return false

    }


    fun toJson() : String {
        return Gson().toJson(this)
    }


    fun startNewEntry() : Week {
        "sw".logit()
        if(! isEntryOpen()) {
            entrySets.add(EntrySet().setInToNow())
            return this
        }else
            throw Exception("error - tried to make new entry without closing the last one")
    }



    //get all entries in a day
    fun getEntriesForDay(d: Date) : MutableList<EntrySet> {
        val entriesOut : MutableList<EntrySet> = MutableList<EntrySet>( 0, { i -> EntrySet() })

        for(e in entrySets)
            if( e.date == d )
                entriesOut.add(e)

        return entriesOut
    }




    fun getEntriesForDay(d:Int) = getEntriesForDay(getDay(d))
    fun getTotalMinutesFromADay(d: Date): Long {
        var minsOut = 0.toLong()
        var dayentries = getEntriesForDay(d)
        for(e in dayentries)
            minsOut += e.duration

        return minsOut
    }

    override fun toString(): String {
        var outstr = "Week ($weekStartDate  -  $weekEndDate):\n"
        for(e in entrySets)
            outstr += "${e.toString()}\n"

        return outstr
    }

    companion object {

        //fun SampleDataJson(): String =
        //    """{"entrySets":[{"inEntry":{"date":{},"dateTime":{},"time":{}},"notes":"","outEntry":{"date":{},"dateTime":{},"time":{}},"tasks":[],"trelloLinks":[]},{"inEntry":{"date":{},"dateTime":{},"time":{}},"notes":"","outEntry":{"date":{},"dateTime":{},"time":{}},"tasks":[],"trelloLinks":[]},{"inEntry":{"date":{},"dateTime":{},"time":{}},"notes":"","outEntry":{"date":{},"dateTime":{},"time":{}},"tasks":[],"trelloLinks":[]},{"inEntry":{"date":{},"dateTime":{},"time":{}},"notes":"","outEntry":{"date":{},"dateTime":{},"time":{}},"tasks":[],"trelloLinks":[]},{"inEntry":{"date":{},"dateTime":{},"time":{}},"notes":"","outEntry":{"date":{},"dateTime":{},"time":{}},"tasks":[],"trelloLinks":[]},{"inEntry":{"date":{},"dateTime":{},"time":{}},"notes":"","outEntry":{"date":{},"dateTime":{},"time":{}},"tasks":[],"trelloLinks":[]},{"inEntry":{"date":{},"dateTime":{},"time":{}},"notes":"","outEntry":{"date":{},"dateTime":{},"time":{}},"tasks":[],"trelloLinks":[]}],"weekStartDate":{}}"""


        fun SampleDataStuff(): Week {


            val w = Week(LocalDate.now())
//LocalDateTime.now().toString().logit()

            w.entrySets.add(
                EntrySet()
                .setIn(LocalDateTime.parse("2022-05-09T10:30")).setOut(LocalDateTime.parse("2022-05-09T11:15")))
            w.entrySets.add(
                EntrySet()
                .setIn(LocalDateTime.parse("2022-05-09T15:00")).setOut(LocalDateTime.parse("2022-05-09T16:00")))
            w.entrySets.add(
                EntrySet()
                .setIn(LocalDateTime.parse("2022-05-09T18:00")).setOut(LocalDateTime.parse("2022-05-09T19:10")))

            w.entrySets.add(
                EntrySet()
                .setIn(LocalDateTime.parse("2022-05-11T10:00")).setOut(LocalDateTime.parse("2022-05-11T12:20")))
            w.entrySets.add(
                EntrySet()
                .setIn(LocalDateTime.parse("2022-05-11T13:00")).setOut(LocalDateTime.parse("2022-05-11T15:00")))

            w.entrySets.add(
                EntrySet()
                .setIn(LocalDateTime.parse("2022-05-12T16:30")).setOut(LocalDateTime.parse("2022-05-12T17:30")))
            w.entrySets.add(
                EntrySet()
                .setIn(LocalDateTime.parse("2022-05-12T14:00")).setOut(LocalDateTime.parse("2022-05-12T15:30")))




            //  println(   w.entrySets[1].date.toString() + "\n\n")

            // println(   w.toString()   + "\n\n")

            return w;
        }

    }




}