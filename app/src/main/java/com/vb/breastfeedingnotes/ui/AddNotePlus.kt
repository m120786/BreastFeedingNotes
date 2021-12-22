package com.vb.breastfeedingnotes.ui

import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.TimeUnit
import android.text.format.DateFormat.format
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vb.breastfeedingnotes.R
import com.vb.breastfeedingnotes.database.Note
import com.vb.breastfeedingnotes.ui.theme.PrimaryVeryLight
import com.vb.breastfeedingnotes.utils.TimeConverter
import com.vb.breastfeedingnotes.viewmodel.NotesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*


@Composable
fun AddNotePlus() {

    val mNotesViewModel: NotesViewModel = viewModel()

    var showDialog by remember { mutableStateOf(false)}

    val context = LocalContext.current as AppCompatActivity

    OutlinedButton(onClick = {showDialog = true}, modifier = Modifier.background(PrimaryVeryLight)) {
        Icon(modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_baseline_playlist_add_24),
            contentDescription = "add_note_button",
            tint = Color.Black,
            )

    }
    if (showDialog) {
        DialogAddNote(onDismiss = {showDialog = !showDialog},
            onAddNote = {mNotesViewModel.addNote(it)},
            onCancel = {showDialog = !showDialog},
            context = context,
            onListChange = {mNotesViewModel.onDateChange(LocalDate.now().toString())})
    }

}

@Composable
fun DialogAddNote(
    onDismiss: () -> Unit,
    onCancel: () -> Unit,
    onAddNote: (Note) -> Unit,
    onListChange: () -> Unit,
    context: Context) {
    var timeDifference = remember {mutableStateOf("")}
    var side: String = ""


    Dialog(onDismissRequest = onDismiss) {
        Card(
            elevation = 5.dp,
            shape = RoundedCornerShape(10.dp)
        )
        {
            Column(modifier = Modifier.padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.add_note),
                    fontSize = 20.sp
                )
                var startTime = TimePicker(context = context, "Pradžia: ")
                Spacer(modifier = Modifier.padding(10.dp))
                var endTime = TimePicker(context = context, "Pabaiga: ")

                if (startTime != 0L && endTime != 0L) {
                    timeDifference.value = timeDiffToString(getTimeDifference(startTime, endTime))
                }
                Text(text = "${timeDifference.value}")
                Spacer(modifier = Modifier.padding(10.dp))
                side = myradioGroup()
                if (side == stringResource(id = R.string.left)) {
                    side = stringResource(id = R.string.left_l)
                } else {
                    side = stringResource(id = R.string.right_r)
                }


                Row(verticalAlignment = Alignment.Bottom) {
                    OutlinedButton(onClick = {
                        val noteObj = Note(0, LocalDate.now(),startTime, endTime, getTimeDifference(startTime, endTime),side)
                        onAddNote(noteObj)
                        onListChange()
                        onCancel()
                    }) {
                        Text(text = stringResource(R.string.add))
                    }
                    Spacer(modifier = Modifier.padding(2.dp))
                    OutlinedButton(onClick = onCancel) {
                        Text(text = stringResource(R.string.cancel))
                    }
                    }
                }
            }
        }
    }

fun getTimeDifference(startTime: Long, endTime: Long): Long {
    val simpleDateFormat = SimpleDateFormat("HH:mm")
    var timeDiff = 0L
    timeDiff = endTime - startTime
    if (timeDiff<0L) {
        val maxTime = simpleDateFormat.parse("24:00").time
        val minTime = simpleDateFormat.parse("00:00").time
        timeDiff = (maxTime-startTime) + (endTime - minTime)
    }
    return timeDiff
}

fun timeDiffToString(timeDiff: Long): String {
    var hours: Long = 0L
    var minutes: Long = 0L
    var stringHM: String = ""
    hours = timeDiff / (60 * 60 * 1000) % 24
    minutes = (timeDiff - hours*60*60*1000) / (1000 * 60)
    stringHM = "${hours} h : ${minutes} min"
    return stringHM
}

@Composable
fun TimePicker(
    context: Context,
    text: String): Long {
    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute= calendar[Calendar.MINUTE]

    var timeConverter = remember{ TimeConverter() }
    var timeLong = remember {mutableStateOf(0L)}

    val simpleDateFormat = SimpleDateFormat("HH:mm")

    val time = remember { mutableStateOf("")}
    val timePickerDialog = TimePickerDialog(
        context,
        {_, hour : Int, minute: Int ->
            val timeString = "$hour:$minute"
//            time.value = "${simpleDateFormatHOURS.parse(hour.toString())}:${simpleDateFormatMIN.parse(minute.toString())}"

            time.value = "${timeConverter.convertTimeFromLongToString(simpleDateFormat.parse(timeString).time)}"
            timeLong.value = simpleDateFormat.parse(timeString).time
        }, hour, minute, true
    )

    Column(modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Text(text = "${text}")
            Spacer(modifier = Modifier.size(16.dp))
            Button(onClick = {
                timePickerDialog.show()
            }) {
                if (time.value == "") {
                    Text("${LocalTime.now().hour}:${LocalTime.now().minute}")
                } else {
                    Text(text = "${time.value}")
                }
            }
        }
    }
    return timeLong.value
}