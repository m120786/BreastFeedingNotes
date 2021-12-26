package com.vb.breastfeedingnotes.notesView.addNote

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.vb.breastfeedingnotes.R
import com.vb.breastfeedingnotes.database.SidePick
import com.vb.breastfeedingnotes.navigation.Screen
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Composable
fun AddNotePlus(
    context: Context,
    addNotePlusViewModel: AddNotePlusViewModel,
    navController: NavController
) {

    val duration = addNotePlusViewModel.duration.collectAsState(initial = Duration.ZERO)
    var side: SidePick


    Dialog(onDismissRequest = {navController.navigate(Screen.MainScreen.route)}) {
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

                val startT = timePicker(context = context, stringResource(id = R.string.start))
                addNotePlusViewModel.setStartTimeDialog(startT)
                Spacer(modifier = Modifier.padding(10.dp))
                val endT = timePicker(context = context, stringResource(id = R.string.end))
                addNotePlusViewModel.setEndTimeDialog(endT)
                addNotePlusViewModel.calculateDuration()                    //sets duration text


                Text(text = "${duration.value}")
                Spacer(modifier = Modifier.padding(10.dp))
                side = SidePick.valueOf(myRadioGroup())
                addNotePlusViewModel.setSide(side)
                Row(verticalAlignment = Alignment.Bottom) {
                    OutlinedButton(onClick = {navController.navigate(Screen.MainScreen.route)}) {
                        Text(text = stringResource(R.string.cancel))
                    }
                    Spacer(modifier = Modifier.padding(2.dp))
                    OutlinedButton(onClick = {
                        addNotePlusViewModel.saveObj()
                        navController.navigate(Screen.MainScreen.route)
                    }) {
                        Text(text = stringResource(R.string.add))
                    }
                    }
                }
            }
        }
    }

@Composable
fun timePicker(
    context: Context,
    text: String): Instant {
    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute= calendar[Calendar.MINUTE]

    val instant = remember {mutableStateOf(Instant.now().truncatedTo(ChronoUnit.MINUTES))}

    val timePickerDialog = TimePickerDialog(
        context,
        {_, hour : Int, minute: Int ->
            var timeString = "$hour:$minute"
            if (hour<10) { timeString = "0$hour:$minute" }                      //Timepicker sometimes gets hours less than 10, IOT parse needs to be 01, 02...
            if (minute<10) {timeString = "$hour:0$minute"}
            if ((hour<10) && (minute<10)) {timeString = "0$hour:0$minute"}

            val time = LocalTime.parse(timeString)
            val ldt = time.atDate(LocalDate.now())
            instant.value = ldt.atZone(ZoneId.systemDefault()).toInstant()
        }, hour, minute, true
    )

    Column(modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(text = text)
            Spacer(modifier = Modifier.size(16.dp))
            Button(onClick = {
                timePickerDialog.show()
            }) {
                    Text("${LocalTime.from(instant.value.atZone(ZoneId.systemDefault()))}")     // gets LocalTime from Instant value

            }
        }
    }
    return instant.value
}