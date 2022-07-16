package com.smol.timclock2.classes

import android.app.Application
import androidx.compose.foundation.ExperimentalFoundationApi
import com.smol.timclock2.ui.MainActivity
import java.lang.Exception
import java.time.DayOfWeek
import java.time.temporal.Temporal
import java.time.temporal.TemporalAccessor
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters


class MyApp : Application() {




    var weekList : WeekList = WeekList()
    lateinit var prefs : PrefWriter
    var firstRun = false

    @ExperimentalFoundationApi
    var mainActivity : MainActivity? = null

    init {

        MyApp.instance = this




    }

    override fun onCreate() {
        super.onCreate()
        MyApp.instance = this

        prefs = PrefWriter(this)

        firstRun = prefs.firstRun
        firstRun.toString().logit()

        "firstRun loaded:\n\t${firstRun.toString()}".logit()

        weekList = prefs.weekList ?: WeekList().plusCurrentWeek()
        //weekList.getCurrentWeek()?.startNewEntry() //
        weekList!!

        "WeekList loaded:\n\t${weekList.toString()}".logit()




    }

    companion object {
        var instance: MyApp? = null
            //get() implied
            private set

    }


}