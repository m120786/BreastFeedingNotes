package com.vb.breastfeedingnotes.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vb.breastfeedingnotes.R
import com.vb.breastfeedingnotes.database.Note
import com.vb.breastfeedingnotes.ui.theme.*
import com.vb.breastfeedingnotes.ui.timer.TimerViewModel
import com.vb.breastfeedingnotes.viewmodel.NotesViewModel
import com.vb.breastfeedingnotes.utils.TimeConverter
import kotlinx.coroutines.*
import java.time.LocalDate


@Composable
fun AddNoteScreen(
    notesViewModel: NotesViewModel,
    timerViewModel: TimerViewModel
) {

    val startTime = notesViewModel.start_time.observeAsState("")
    val startTimeT = rememberSaveable { mutableStateOf(0L) }
//    val currentDate = rememberSaveable { mutableStateOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))) }
    val currentDate = rememberSaveable { mutableStateOf(LocalDate.now()) }
    val endTimeT = rememberSaveable { mutableStateOf(0L) }
    val durationT = rememberSaveable { mutableStateOf(0L) }
    val side = rememberSaveable { mutableStateOf("") }
    val timeConverter = TimeConverter()

    val timerViewModelRunning = timerViewModel.isRunning.collectAsState().value

    var isTimerRunning = rememberSaveable { mutableStateOf(false) }
    val isEnabledEndTime = rememberSaveable { mutableStateOf(false) }
    val isEnabledStartTime = rememberSaveable { mutableStateOf(true) }

    @Composable
    fun MyStartTimeButton() {
        FloatingActionButton(
            onClick = {
                notesViewModel.onStartTimeChange(timeConverter.convertTimeFromLongToString(System.currentTimeMillis()))
                startTimeT.value = System.currentTimeMillis()
                currentDate.value = LocalDate.now()

                isEnabledEndTime.value = true
                isEnabledStartTime.value = false
                isTimerRunning.value = true
            },
            backgroundColor = SecondaryDark, modifier = Modifier.padding(4.dp)
        ) {
            Icon(Icons.Filled.PlayArrow, "", tint = Color.White)
        }
    }

    @Composable
    fun MyEndTimeButton() {
        fun sideTextLogic(side: String): String {
            if (side == "Kairė") {
                return "K"
            } else {
                return "D"
            }
        }

        FloatingActionButton(onClick = {
            notesViewModel.onEndTimeChange(timeConverter.convertTimeFromLongToString(System.currentTimeMillis()))
            endTimeT.value = System.currentTimeMillis()
            durationT.value = (endTimeT.value - startTimeT.value)
            notesViewModel.onDurationTimeChange(timeConverter.getDisplayValue(durationT.value))
            isTimerRunning.value = false

            val noteObj = Note(
                0,
                currentDate.value,
                startTimeT.value,
                endTimeT.value,
                durationT.value,
                sideTextLogic(side.value)
            )
            GlobalScope.launch(Dispatchers.IO) {
                notesViewModel.addNote(noteObj)
                withContext(Dispatchers.Main) {
                    notesViewModel.onDateChange(LocalDate.now().toString())
                }
            }
            notesViewModel.onStartTimeChange("")
            notesViewModel.onEndTimeChange("")
            notesViewModel.onSideChange("")
            notesViewModel.onDurationTimeChange("")
        }, backgroundColor = SecondaryDark, modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(R.drawable.ic_stop), "", tint = Color.White)
        }

    }


    @Composable
    fun Timer(viewModel: TimerViewModel) {
        if (isTimerRunning.value) {
            if (timerViewModelRunning) {
                val totalTimePassed by viewModel.timeSec.observeAsState()
                val timePassedInMinutes by viewModel.timeMin.observeAsState()
                Text(
                    "" + timePassedInMinutes + " MIN " + totalTimePassed + " SEC",
                    color = Color.Black,
                    style = Typography.h6
                )
            } else {
                viewModel.startTimer()
                Text(
                    "" + 0 + " MIN " + 0 + " SEC",
                    color = Color.Black,
                    style = Typography.h6
                )
            }
        } else {
            viewModel.stopTimer()
            Text(
                "" + 0 + " MIN " + 0 + " SEC",
                color = Color.Black,
                style = Typography.h6
            )
        }
    }

    @Composable
    fun MyChangeStateButton() {
        if (!isTimerRunning.value) {
            MyStartTimeButton()
        } else {
            MyEndTimeButton()
        }
    }


    /*
    MAIN COMPOSABLE VIEW
     */


    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color.White)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(0.5F)) {
                    Row(modifier = Modifier.padding(2.dp)) {
                        Spacer(modifier = Modifier.padding(2.dp))
                        Text(
                            text = " ${startTime.value}",
                            color = Color.Black,
                            style = Typography.h6
                        )
                    }
                    Timer(timerViewModel)
                }
                AddNotePlus(notesViewModel)
            }
            side.value = myradioGroup()
            Spacer(modifier = Modifier.padding(2.dp))

            /*
            START/STOP BUTTON
             */

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                MyChangeStateButton()
            }
        }
    }
}


@Composable
fun myradioGroup(): String {

    var selected by rememberSaveable { mutableStateOf("Kairė") }
    val radioGroupOptions = listOf("Kairė", "Dešinė")

    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 2.dp,
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        val onSelectedChange = { text: String -> selected = text }

        Row(verticalAlignment = Alignment.CenterVertically) {
            radioGroupOptions.forEach { item ->
                val color = if (selected == item) PrimaryDark else Grey
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .background(color)
                        .selectable(
                            selected = (item == selected),
                            onClick = {
                                onSelectedChange(item)
                            })
                        .padding(16.dp)
                        .weight(1F)
                ) {
                    Text(
                        text = item,
                        color = Color.White,
                        style = MaterialTheme.typography.h6.merge()
                    )
                }
            }

        }
    }
    return selected
}

