package com.vb.breastfeedingnotes.views.notesView.addNote

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vb.breastfeedingnotes.R
import com.vb.breastfeedingnotes.database.notes.SidePick
import com.vb.breastfeedingnotes.navigation.Screen
import com.vb.breastfeedingnotes.views.notesView.NotesViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Composable
fun AddNotePlus(
    navController: NavController
) {
    val addNotePlusViewModel = hiltViewModel<AddNotePlusViewModel>()
    val notesListViewModel = hiltViewModel<NotesViewModel>()
    val duration = addNotePlusViewModel.duration.collectAsState(initial = Duration.ZERO)
    var timePickerStart by rememberSaveable { mutableStateOf(false) }
    var timePickerEnd: Boolean by rememberSaveable { mutableStateOf(false) }

    val timePickerStartTime = addNotePlusViewModel.startTime.collectAsState(Instant.now())
    val timePickerEndTime = addNotePlusViewModel.endTime.collectAsState(Instant.now())

    Dialog(onDismissRequest = { navController.popBackStack() }) {
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

                OutlinedButton(onClick = { timePickerStart = !timePickerStart }) {
                    Text(text = "${timePickerStartTime.value.toTime()}")
                }
                /*
                START TIME PICKER
                 */
                if (timePickerStart) {
                    Dialog(onDismissRequest = { timePickerStart = false }) {
                        AndroidView(
                            factory = { context: Context ->
                                LayoutInflater.from(context).inflate(R.layout.customtimepicker, null, false)
                            },
                            modifier = Modifier.fillMaxWidth()
                                .background(Color.White),
                            update = { view ->
                                val setTimeButton = view.findViewById<Button>(R.id.btn_set_time)
                                val cancelButton = view.findViewById<Button>(R.id.btn_cancel)
                                val simpleTimePicker = view.findViewById<TimePicker>(R.id.simpleTimePicker)
                                simpleTimePicker.setIs24HourView(true)
                                setTimeButton.setOnClickListener {
                                    addNotePlusViewModel.startTime.value = convertToInstant(simpleTimePicker.hour, simpleTimePicker.minute)
                                    timePickerStart = false
                                }
                                cancelButton.setOnClickListener {
                                    timePickerStart = false
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
                /*
                END TIME PICKER
                 */
                OutlinedButton(
                    onClick = { timePickerEnd = !timePickerEnd }) {
                    Text(text = "${timePickerEndTime.value.toTime()}")
                }
                if (timePickerEnd) {
                    Dialog(onDismissRequest = { timePickerEnd = false }) {
                        AndroidView(
                            factory = { context: Context ->
                                LayoutInflater.from(context).inflate(R.layout.customtimepicker, null, false)
                            },
                            modifier = Modifier.fillMaxWidth()
                                .background(Color.White),
                            update = { view ->
                                val setTimebutton = view.findViewById<Button>(R.id.btn_set_time)
                                val cancelButton = view.findViewById<Button>(R.id.btn_cancel)
                                val simpleTimePicker = view.findViewById<TimePicker>(R.id.simpleTimePicker)
                                simpleTimePicker.setIs24HourView(true)
                                setTimebutton.setOnClickListener {
                                    addNotePlusViewModel.endTime.value = convertToInstant(simpleTimePicker.hour, simpleTimePicker.minute)
                                    addNotePlusViewModel.calculateDuration()
                                    timePickerEnd = false

                                }
                                cancelButton.setOnClickListener {
                                    timePickerEnd = false
                                }
                            }
                        )
                    }
                }
                Text(text = "${duration.value}")
                Spacer(modifier = Modifier.padding(10.dp))
                val side = SidePick.valueOf(myRadioGroup())
                addNotePlusViewModel.sideDialog.value = side
                Row(verticalAlignment = Alignment.Bottom) {
                    OutlinedButton(onClick = { navController.popBackStack() }) {
                        Text(text = stringResource(R.string.cancel))
                    }
                    Spacer(modifier = Modifier.padding(2.dp))
                    OutlinedButton(onClick = {
                        addNotePlusViewModel.saveObj()
                        notesListViewModel.selectedDate.value = LocalDate.now()     //refreshes notesList
                        navController.navigate(Screen.MainScreen.route)
                    }) {
                        Text(text = stringResource(R.string.add))
                    }
                }
            }
        }
    }
}

/*
CONVERTS FROM INSTANT TO LOCAL TIME IN FORMAT 00:00
 */
fun Instant.toTime(): LocalTime {
    return LocalTime.from(this.atZone(ZoneId.systemDefault())).truncatedTo(ChronoUnit.MINUTES)
}

/*
CONVERTS FROM TIMEPICKER TO INSTANT
 */
fun convertToInstant(hour: Int, minute: Int): Instant {
    var timeString = "$hour:$minute"
    if (hour < 10) {
        timeString = "0$hour:$minute"
    }
    if (minute < 10) {
        timeString = "$hour:0$minute"
    }
    if ((hour < 10) && (minute < 10)) {
        timeString = "0$hour:0$minute"
    }
    val time = LocalTime.parse(timeString)
    val ldt = time.atDate(LocalDate.now())

    return ldt.atZone(ZoneId.systemDefault()).toInstant()
}
