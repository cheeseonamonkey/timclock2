package com.smol.timclock2.ui

import android.app.TimePickerDialog
import android.widget.TimePicker
import com.smol.timclock2.R
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.smol.timclock2.classes.*





@ExperimentalFoundationApi
@Composable
//@Preview("Entries_Column")
fun Entries_Column(itemList : SnapshotStateList<EntrySet>
    /*= MyApp.instance!!.weekList.getCurrentWeek()!!.entrySets.toMutableStateList()*/
) {




    LazyColumn( modifier = Modifier.padding(1.dp)) {

        if(itemList.size > 0)

            items(itemList) {



                "\tcomposing - \t${it.inEntry}".logit()


                var dialog_editDialog = remember { mutableStateOf(false) }
                editDialog(dialog_editDialog)




                Box(
                    modifier = Modifier
                        .padding(horizontal = 2.dp, vertical = 4.dp)
                        .background(color = Color(105, 165, 191).copy(alpha = 0.18f))
                        .wrapContentSize()
                        .shadow(2.dp)
                ) {

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(0.3f)) {
                            Column(modifier = Modifier.wrapContentHeight()) {

                                Text(
                                    "${it.date.formatted()}",
                                    modifier = Modifier
                                        .padding(1.dp)
                                        .padding(start = 3.dp)
                                        .fillMaxWidth()
                                    ,
                                    textDecoration = TextDecoration.Underline,
                                )
                                Text(
                                    "${it.inEntry?.time.formatted()}",
                                    modifier = Modifier
                                        .shadow(1.dp)
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                        .padding(start = 3.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            TimePickerDialog(
                                                MyApp.instance!!.mainActivity!!,
                                                TimePickerDialog.OnTimeSetListener { v, hrs, mins ->


                                                    it.setIn(Time.of(hrs,mins))

                                                    MyApp.instance!!.mainActivity!!.refreshEntriesState()


                                                },
                                                2, 22,
                                                false
                                            ).show()
                                        },
                                    fontWeight = FontWeight.Bold,
                                )
                                Text(
                                    "${it.outEntry?.time.formatted()}",
                                    modifier = Modifier
                                        .shadow(1.dp)
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            TimePickerDialog(
                                                MyApp.instance!!.mainActivity!!,
                                                TimePickerDialog.OnTimeSetListener { v, hrs, mins ->



                                                    it.setOut(Time.of(hrs,mins))

                                                    MyApp.instance!!.mainActivity!!.refreshEntriesState()


                                                },
                                                2, 22,
                                                false
                                            ).show()
                                        },
                                    textAlign = TextAlign.End,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            Text(
                                "${it.duration.minutesToHoursMinutesString()}",
                                modifier = Modifier
                                    .padding(top = 6.dp, start = 6.dp)
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(1.dp),
                                fontStyle = FontStyle.Italic,
                            )


                            //it.toString().logit()

                        }
                        Column(modifier = Modifier
                            .weight(0.6f)
                            .fillMaxHeight()

                        ) {

                            Text(
                                when (it.tasks.isEmpty()) {
                                    true -> {
                                        "(no tasks set)".addEmptyLines(3)
                                    }
                                    false -> {
                                        it.tasks.toString()
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(1.dp)
                                    .padding(start = 5.dp, top = 3.dp)
                                    .border(BorderStroke(1.dp, color = Color.Black))
                                    .padding(4.dp)
                                    .combinedClickable(
                                        onClick = { },
                                        onLongClick = { })
                                    .background(Color(240, 242, 206).copy(alpha = 0.1f)),
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Monospace,
                                maxLines = 6
                            )

                        }
                        Column(
                            modifier = Modifier
                                .weight(0.1f)
                                .wrapContentHeight()
                        ) {

                            //button - paste trello link
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .combinedClickable(
                                        onClick = {
                                            //dialog_editDialog.value = true

                                        },
                                        onLongClick = {}
                                    )
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.edit),
                                    contentDescription = "Edit",
                                )
                            }
                            //button - go to external link
                            Box(
                                modifier = Modifier
                                    .padding(0.dp)

                                    .shadow(2.dp)
                                    .size(45.dp)
                                    //.align(Alignment.Bottom)
                                    .combinedClickable(
                                        onClick = {

                                            MyApp.instance!!.weekList!!.getWeekOfDate(it.date)
                                                .entrySets.remove(it)



                                            MyApp.instance!!.mainActivity!!.refreshEntriesState()
                                        },
                                        onLongClick = {}
                                    )
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.trash),
                                    contentDescription = "Delete",
                                )
                            }
                        }
                    }
                }









            }

        item {
            Box(
                modifier = Modifier

                    .fillMaxWidth()
                    .padding(0.dp)
                    .height(250.dp),
            ) {

            }
        }




    }

}

//}


@Composable
fun editDialog(isOn :MutableState<Boolean>) {
    if(isOn.value)
        Dialog(
            onDismissRequest = {isOn.value = false},
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
            ) {
                Text("Hi")
            }

        }

}
