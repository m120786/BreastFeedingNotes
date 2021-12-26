package com.vb.breastfeedingnotes.notesView.addNote


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
import androidx.compose.ui.unit.dp
import com.vb.breastfeedingnotes.R
import com.vb.breastfeedingnotes.database.SidePick
import com.vb.breastfeedingnotes.ui.theme.*
import com.vb.breastfeedingnotes.notesView.timer.TimerViewModel
import com.vb.breastfeedingnotes.notesView.NotesViewModel
import kotlin.time.ExperimentalTime


@ExperimentalTime
@Composable
fun AddNote(notesViewModel: NotesViewModel, timerViewModel: TimerViewModel) {
    val timerViewModelRunning = timerViewModel.isRunning.collectAsState().value
    val isTimerRunning = rememberSaveable { mutableStateOf(false) }

    @Composable
    fun MyStartTimeButton() {
        FloatingActionButton(
            onClick = {
                notesViewModel.getStartTime()
                isTimerRunning.value = true
            },
            backgroundColor = SecondaryDark, modifier = Modifier.padding(4.dp)
        ) {
            Icon(Icons.Filled.PlayArrow, "", tint = Color.White)
        }
    }
    @Composable
    fun MyEndTimeButton() {
        FloatingActionButton(onClick = {
            notesViewModel.getDurationAndSave()
            isTimerRunning.value = false
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
                    text = "$timePassedInMinutes MIN $totalTimePassed SEC",
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
                    Timer(timerViewModel)
                }
            }
            val side: String = myRadioGroup()
            notesViewModel.setSide(SidePick.valueOf(side))
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
fun myRadioGroup(): String {

    var selected by rememberSaveable { mutableStateOf("Left") }
    val radioGroupOptions = listOf("Left", "Right")

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

