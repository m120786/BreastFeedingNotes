package com.vb.breastfeedingnotes.ui

import android.widget.CalendarView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.vb.breastfeedingnotes.database.weight.WeightViewModel
import com.vb.breastfeedingnotes.views.notesView.NotesViewModel
import java.time.LocalDate
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Composable
fun WeightDatePicker() {

    val viewModel = hiltViewModel<WeightViewModel>()

    val selectedDate by viewModel.weightSelectedDate.collectAsState(initial = LocalDate.now())
    var showDatePicker by remember { mutableStateOf(false) }



    OutlinedButton(
        onClick = { showDatePicker = true },
        modifier = Modifier.padding(4.dp),
        border = BorderStroke(1.dp, Color.Black)
    )
    {
        Icon(imageVector = Icons.Filled.DateRange, "date_range_icon")
        Text(
            text = "Weigh date: ${selectedDate}"
        )
    }
    if (showDatePicker) {
        Dialog(onDismissRequest = { showDatePicker = false}) {
            AndroidView(
                { CalendarView(it) },
                modifier = Modifier.wrapContentWidth()
                    .background(Color.White),
                update = { views ->
                    views.setOnDateChangeListener { calendarView, i, i2, i3 ->
                        var dateString ="$i-${(i2+1)}-$i3"
                        if (i2<10) { dateString = "$i-0${(i2 + 1)}-$i3" }
                        if (i3<10) { dateString = "$i-${(i2 + 1)}-0$i3" }
                        if (i2<10 && i3 < 10) { dateString = "$i-0${(i2 + 1)}-0$i3" }

                        val date = LocalDate.parse(dateString)
                        viewModel.weightSelectedDate.value = date
                        showDatePicker = false
                    }
                }
            )
        }
    }
}