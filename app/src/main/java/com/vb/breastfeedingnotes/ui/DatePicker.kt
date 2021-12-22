package com.vb.breastfeedingnotes.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.vb.breastfeedingnotes.viewmodel.NotesViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@Composable
fun DatePickerView() {
    val viewModel: NotesViewModel = viewModel()
    val activity = LocalContext.current as AppCompatActivity

    val calendarDate by viewModel.selectedDate.collectAsState(initial = LocalDate.now().toString())



    val updateDate = { date: Long? ->
        var dateString = DateFormater(date)!!
        viewModel.onDateChange(dateString)
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

fun DateFormater(milliseconds: Long?): String? {
    milliseconds?.let {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE)
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(it)
        return formatter.format(calendar.getTime())
    }
    return null
}