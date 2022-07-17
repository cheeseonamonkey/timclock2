package com.smol.timclock2.ui



import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.smol.timclock2.classes.*
import com.smol.timclock2.ui.theme.Timclock2Theme
import kotlinx.coroutines.delay
import androidx.compose.ui.zIndex
import com.google.gson.Gson
import com.smol.timclock2.R
import com.smol.timclock2.htmlinvoice.InvoiceBuilder


fun String.addEmptyLines(lines: Int) = this + "\n".repeat(lines)

fun SnapshotStateList<EntrySet>.swapList( newlist : MutableList<EntrySet>) {
    "swapping list".logit()
    clear()
    addAll(newlist)
}


@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    val entriesListM = mutableStateListOf<EntrySet>() .apply {
        swapList(MyApp.instance!!.weekList!!.allEntrySets.toMutableList())
    }

    fun refreshEntriesState() = entriesListM.swapList(MyApp.instance!!.weekList!!.allEntrySets.toMutableList())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //dependency injection into MyApp for dialogs
        MyApp.instance!!.mainActivity = this


        setContent {

            Timclock2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    //color = MaterialTheme.colors.background
                ) {

                    Together()


                }
            }
        }
    }


    @Composable
    fun dialogDebugBoop(dialog_debugBoop : MutableState<Boolean>){



        if(dialog_debugBoop.value)
            Dialog(
                onDismissRequest = {dialog_debugBoop.value = false},
            ) {
                Box {
                    Column {
                        Text("Debug boops:")
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp),
                            onClick = {

                                val wl = MyApp.instance?.weekList

                                if(MyApp.instance?.weekList?.weeks?.isEmpty() == true) {
                                    MyApp.instance?.weekList?.plusCurrentWeek()
                                }


                                var w = MyApp.instance?.weekList?.getCurrentWeek()
                                w = Week.SampleDataStuff()

                                Toast.makeText(applicationContext,"sample data added", Toast.LENGTH_SHORT).show()

                                refreshEntriesState()

                                dialog_debugBoop.value = false

                            }
                        ) {
                            Text("Add sample data")
                        }



                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp),
                            onClick = {

                                Gson().toJson(MyApp.instance?.weekList).logit()



                            }
                        ) {
                            Text("Take a dump (log)")
                        }




                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp),
                            onClick = {

                                InvoiceBuilder.invoiceHtml()

                                dialog_debugBoop.value = false

                            }
                        ) {
                            Text("Test HTML generate")
                        }

                    }
                }
            }


    }



    @ExperimentalFoundationApi
   // @Preview("Together")
    @Composable
    fun Together() {



        Column(modifier = Modifier
            ,
        ) {

            Box {
                //top box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .zIndex(0f)
                        .padding(3.dp)
                    //.weight(0.64f)
                ) {





                    //      current list or blank
                    //      (was mistakenly placed AFTER composable, then got handled elsewhere on the call stack)

                    // entriesListM.swapList(
                    // MyApp.instance?.weekList?.getCurrentWeek()?.entrySets ?: MutableList<EntrySet>( 0, { i -> EntrySet() }))



                    Entries_Column(entriesListM)

                }

                //bottom box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .zIndex(1f)
                        .padding(2.dp)
                        .shadow(1.dp)
                        .padding(2.dp)
                    //.weight(0.36f)
                ) {
                    Controls_Panel()
                }


            }
        }
    }



    @ExperimentalFoundationApi
    @Composable
    @Preview("Controls_Panel")
    fun Controls_Panel() {

        Box(modifier = Modifier
            .wrapContentHeight()
            .background(Color.Yellow.copy(alpha = 0.05f))
        ) {


            Column {

                Row(
                    modifier = Modifier
                ) {

                    //left side control panel
                    ControlPanelLeft(Modifier.weight(0.62f))

                    //right side control panel
                    ControlPanelRight(Modifier.weight(0.38f))

                }




                //
                //bottom input controls


                //tasks input
                Text("Tasks:",
                    modifier = Modifier,
                    fontSize = 9.sp)
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp, vertical = 3.dp)
                        .padding(end = 15.dp)
                        .background(Color(174, 195, 210).copy(alpha = 0.7f))
                    ,
                    value = TextFieldValue("".addEmptyLines(1)),
                    onValueChange = {  },
                    maxLines = 7
                )


                //link input
                Text("Trello link:",
                    modifier = Modifier,
                    fontSize = 9.sp)
                Row {
                    Text(
                        "(tap to paste link)",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp, vertical = 4.dp)
                            .padding(end = 15.dp)
                            .background(Color(174, 195, 210).copy(alpha = 0.7f))
                            .combinedClickable(
                                onClick = {},
                                onLongClick = {}
                            ),
                        fontSize = 10.sp,
                        maxLines = 1,


                        )


                }



            }
        }

    }

    @ExperimentalFoundationApi
    @Composable
    fun ControlPanelLeft(modifierPassed : Modifier) {
        Box(
            modifier = modifierPassed
                .wrapContentWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.BottomCenter){
        Column(

            modifier = Modifier

                .padding(end = 50.dp, bottom = 6.dp, start = 5.dp)
                //.weight(0.45f)
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.DarkGray.copy(alpha = 0.92f))
                .shadow(1.dp)
                .padding(3.dp)
                .background(Color.LightGray.copy(alpha = 0.92f)),

            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,


            ) {

            //  remember { mutableStateOf(Time.now()) }

            var dialog_debugBoopOn = remember { mutableStateOf(false) }

            dialogDebugBoop(dialog_debugBoopOn)


            var nowtime = remember { mutableStateOf(Time.now()) }
            var nowdate = remember { mutableStateOf(Date.now()) }
            HSMCurrentTimeEffect(nowtime, nowdate)


            //current time display
            Text(

                "${nowtime.value.formatted()}",
                modifier = Modifier
                    .padding(4.dp)
                    .padding(top = 10.dp)
                    .padding(bottom = 20.dp)
                    .shadow(1.dp)
                    .padding(horizontal = 8.dp, 2.dp)
                    .pointerInput("k1", "k2") {
                        detectTapGestures(
                            onDoubleTap = {
                                dialog_debugBoopOn.value = true

                            })
                    },
                fontSize = 25.sp
            )
            //current date
            Text(
                "${Date.now().formatted()}",
                modifier = Modifier
                    .padding(2.dp),
                fontSize = 12.sp
            )





            Column(
                modifier = Modifier
                    .padding(2.dp)
                    .border(BorderStroke(1.dp, Color.Black.copy(alpha = 0.3f)))
                    .padding(1.dp)
            ) {

                //total today counter
                Row(
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 2.dp)
                ) {
                    Text(
                        "Total today:", modifier = Modifier
                            .wrapContentHeight()
                            .padding(end = 6.dp),
                        fontSize = 11.sp,
                        maxLines = 1
                    )
                    Text(
                        MyApp.instance?.weekList?.getCurrentWeek()
                            ?.getTotalMinutesFromADay(Date.now())?.minutesToHoursMinutesString()
                            ?: "none",
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        fontSize = 11.sp,
                        maxLines = 1
                    )
                }

                //total week counter
                Row(
                    modifier = Modifier
                        .padding(horizontal = 5.dp, 2.dp)
                ) {
                    Text(
                        "Total week:",
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(end = 6.dp),
                        fontSize = 11.sp,
                        maxLines = 1
                    )
                    Text(
                        MyApp.instance?.weekList?.getCurrentWeek()?.totalMinutes?.minutesToHoursMinutesString()
                            ?: "none", modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        fontSize = 11.sp,
                        maxLines = 1
                    )
                }

            }

        }
    }
    }

    @ExperimentalFoundationApi
    @Composable
    fun ControlPanelRight(modifierPassed : Modifier) {
        Box(
            modifier = modifierPassed
                //.weight(0.55f)
                .fillMaxWidth()
                .fillMaxHeight()
                , contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier

                ,

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){




                val playPainterId = R.drawable.player_play

                val playOrPauseId = remember {
                    mutableStateOf(playPainterId)
                }

                //play button
                Box(
                    modifier = Modifier
                        .padding(top = 15.dp, end = 15.dp)
                        .clip(CircleShape)
                        .background(Color(43, 200, 68))
                        .size(150.dp)
                        .combinedClickable(
                            onClick = {

                                //
                                var wl = MyApp.instance?.weekList!!

                                //if no weeks / no current week
                                if (wl.weeks.isEmpty() || !wl.hasCurrentWeek())
                                //add current week
                                    wl = wl.plusCurrentWeek()

                                //
                                var w = wl.getCurrentWeek()!!


                                //w = Week.SampleDataStuff()    //set sample data (not current week)

                                if (w.isEntryOpen()) {
                                    //change button UI and close entry
                                    w
                                        .getOpenEntry()
                                        .setOutToNow()


                                    playOrPauseId.value = R.drawable.player_play


                                } else {
                                    //change button UI and open new entry
                                    w = w.startNewEntry()
                                    playOrPauseId.value = R.drawable.hourglass
                                }






                                refreshEntriesState()
                                //


                            },
                            onLongClick = {}
                        )

                ) {






                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center),
                        painter = painterResource(id = playOrPauseId.value),
                        contentDescription = "Start/stop timeclock"
                    )


                }

                var stimer = remember { mutableStateOf<Long>(0) }
                var mtimer = remember { mutableStateOf<Long>(0) }
                var htimer = remember { mutableStateOf<Long>(0) }


                HSMTimerEffect(h = htimer, s = stimer, m = mtimer)
                HSMElapsedDisplayer(h = htimer, s = stimer, m = mtimer) //displayer

            }
        }
    }

    @ExperimentalFoundationApi
    @Composable
    fun HSMCurrentTimeEffect(n : MutableState<Time>, dn : MutableState<Date>) {
        LaunchedEffect(key1 = Unit) {
            while (true) {

                delay(1000)
                n.value = Time.now()


            }
        }
        LaunchedEffect(key1 = Unit) {
            while (true) {

                delay(2000000 )
                dn.value = Date.now()


            }
        }
    }

    @Composable
    fun HSMTimerEffect(h : MutableState<Long>, s : MutableState<Long>, m : MutableState<Long>) {
        LaunchedEffect(key1 = Unit) {
            while (true) {

                delay(1000)
                s.value++

                if (s.value.equals(60)) {
                    s.value = 0;
                    m.value++

                    if (m.value.equals(60)) {
                        m.value = 0;
                        h.value++

                        if(h.value .equals(25))
                            h.value = 24

                    }

                }
            }
        }
    }

    @Composable
    fun HSMElapsedDisplayer(h: MutableState<Long>, s: MutableState<Long>, m: MutableState<Long>) {




        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color.DarkGray.copy(alpha = 1f))
                .padding(5.dp)
        ) {
            Text(
                "from \n ${s.value}\n" +
                "",//TODO: stopwatch
                // ${(MyApp.instance!!.weekList.getCurrentWeek()?.entrySets?.last()?.inEntry?.time?.plusSeconds(s.value))}",
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp),
                color = Color.White,
                fontSize = 15.sp
            )
            Text(
                "",
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp, start = 10.dp)

                    .align(Alignment.CenterVertically),
                color = Color.White,
                fontSize = 13.sp
            )
        }
    }



}
