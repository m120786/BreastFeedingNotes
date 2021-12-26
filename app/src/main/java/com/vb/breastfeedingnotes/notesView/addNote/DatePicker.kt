package com.vb.breastfeedingnotes.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.material.datepicker.MaterialDatePicker
import com.vb.breastfeedingnotes.notesView.NotesViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Composable
fun DatePickerView(viewModel: NotesViewModel) {
    val activity = LocalContext.current as AppCompatActivity
    val calendarDate by viewModel.selectedDate.collectAsState(initial = LocalDate.now().toString())



    val updateDate = { date: Long? ->
        val dateString = dateFormater(date)!!
        viewModel.setDate(LocalDate.parse(dateString))
    }
        OutlinedButton(
            onClick = { showDatePicker(activity, updateDate) },
            modifier = Modifier.padding(4.dp),
            border = BorderStroke(1.dp, Color.Black)
        )
        {
            Icon(imageVector = Icons.Filled.DateRange, "date_range_icon")
            Text(
                text = "${calendarDate}"
            )
        }
}


private fun showDatePicker(
    activity: AppCompatActivity,
    updateDate: (Long?) -> Unit
) {
    val picker = MaterialDatePicker.Builder.datePicker().build()
    picker.show(activity.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener {
        updateDate(it)
    }
}

fun dateFormater(milliseconds: Long?): String? {
    milliseconds?.let {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(it)
        return formatter.format(calendar.getTime())
    }
    return null
}