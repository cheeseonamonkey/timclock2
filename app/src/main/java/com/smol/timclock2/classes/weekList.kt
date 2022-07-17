package com.smol.timclock2.classes

import com.google.gson.Gson
import java.lang.Exception
import java.time.DayOfWeek
import java.time.temporal.TemporalAdjusters




data class WeekList(
    val weeks :MutableList<Week> = mutableListOf()
) {

val allEntrySets : List<EntrySet>
    get() {
        val newl :MutableList<EntrySet> = mutableListOf()
        for(w in weeks)
            newl.addAll(w.entrySets)
        return newl.toList()
    }

    operator fun iterator() = weeks.iterator()
//    operator fun get(int: Int) = weeks.get(int)
    operator fun inc() :WeekList = plusCurrentWeek()

    fun isAnEntryOpen() :Boolean {
        for(w in this)
            if(w.isEntryOpen())
                return true
        //else
        return false
    }

    fun sort() :WeekList {
        for(w in this)
            w.entrySets.sortBy { it.date }
        return this
    }



    fun getCurrentWeek() :Week?{
        if(this.hasCurrentWeek()) {
            val now = DateTime.now()
            val matchingWeeks : MutableList<Week> = mutableListOf()
            for(w in this)
                if(now.isAfter(w.weekStartDate.atStartOfDay())  &&
                    now.isBefore(w.weekEndDate.plusDays(1).atStartOfDay())) {
                    //found week
                    matchingWeeks.add(w)
                }
            if(matchingWeeks.size == 0)
                return null
            else if(matchingWeeks.size == 1)
                return matchingWeeks[0]
            else if(matchingWeeks.size > 1)
                throw Exception("Error 122 - Multiple current weeks exist! This shouldn't happen.");
            else
                throw Exception("Wacky error 129")

        }else

            "no week found - creating".logit()
        MyApp.instance?.weekList.toString().logit()
            weeks.add(getNewCurrentWeek())
            return getCurrentWeek()



    }

    fun hasCurrentWeek() :Boolean {

        val now = DateTime.now()
        val matchNow = mutableListOf<Week>()
        for(w in this.weeks) {
            //w.weekEndDate.plusDays(1).atStartOfDay().toString().logit()
            if(now.isAfter(w.weekStartDate.atStartOfDay())  &&
                now.isBefore(w.weekEndDate.plusDays(1).atStartOfDay()))
                matchNow.add(w)
        }

        if(matchNow.size == 1)
            return true

        if(matchNow.size > 1)
            throw Exception("Err 123 - Multiple current weeks existing at once!")

        //else nothing found
        return false
    }

fun getWeekOfDate(dt:Date) : Week {


    val matchNow = mutableListOf<Week>()
    for(w in this.weeks) {
        //w.weekEndDate.plusDays(1).atStartOfDay().toString().logit()
        if(dt.isAfter(w.weekStartDate)  &&
            dt.isBefore(w.weekEndDate.plusDays(1)))
            matchNow.add(w)
    }

    if(matchNow.size == 1)
        return matchNow.last()

    if(matchNow.size > 1)
        throw Exception("Err 132 - Multiple weeks found for date: ${dt.toString()}")

    //else nothing found
    throw Exception("Err 133 - No weeks found for date: ${dt.toString()}")
}


    fun plusCurrentWeek(): WeekList {

        "plus current week - ".logit()
        //this.toString().logit()

        if(this.hasCurrentWeek())
            throw Exception("Error 121 - Week already exists! Don't do this.");

        val newl = this.weeks.toMutableList()
        newl.add(this.getNewCurrentWeek())
        return WeekList(newl)
        //val newlOut = newl.toList()
        //return WeekList(newlOut)
    }

    private fun getNewCurrentWeek(): Week {
        val startdate = Date.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY))
        val newweek = Week(startdate)
        return newweek
    }

    fun toJson() : String {
        return Gson().toJson(this)
    }

    override fun toString() : String {
        var strOut = "\tWeekList:\n"
        for(w in this){ strOut += "${w.toString()}\n" }
        return strOut
    }

}