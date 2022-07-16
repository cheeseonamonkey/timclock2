package com.smol.timclock2.classes


import android.content.Context
import android.util.Log
import com.google.gson.Gson



class PrefWriter(val context: Context) {



    private val prefs by lazy { context.getSharedPreferences(Keys.APP_PREF_KEY_GLOBAL, Context.MODE_PRIVATE) }
    val gson by lazy { Gson() }

    //pref keys
    object Keys {

        //global app prefs key
        const val APP_PREF_KEY_GLOBAL = "_globalAppKey"

        const val WEEK_LIST = "weekListJson"
        const val FIRST_RUN = "firstRunOfApp"
    }

    //

    val firstRun : Boolean
        get () {
            val key = Keys.FIRST_RUN
            val p = prefs.getString(key, null)
            if(p == null) {
                Log.d("PrefRead", "pref read - first run = TRUE")
                prefs.edit().putString(key, "beenRun").apply()
                return true
            }
            else {
                Log.d("PrefRead", "pref read - first run = FALSE")
                return false
            }



        }






    var weekList : WeekList?
        get() = gson.fromJson((weekListJson), WeekList::class.java)


        set(value :WeekList?) {
            weekListJson = gson.toJson(value)
        }






    //
    //private JSON getters:
    //

    private var weekListJson : String?
        get () {
            val key = Keys.WEEK_LIST
            val p = prefs.getString(key, null)
            Log.d("PrefRead", "pref read - weekListJson: $p")
            return p
        }
        set (value) {
            val key = Keys.WEEK_LIST
            prefs.edit().putString(key, value).apply()
            Log.d("PrefWrite", "pref write - weekListJson: ${value}  (checkback: ${weekListJson})")
        }




}