package com.vb.breastfeedingnotes.ui

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vb.breastfeedingnotes.R
import com.vb.breastfeedingnotes.database.Note
import com.vb.breastfeedingnotes.utils.TimeConverter
import com.vb.breastfeedingnotes.viewmodel.NotesViewModel
import kotlinx.coroutines.*
import java.time.LocalDate


@Composable
fun NotesList(viewModel: NotesViewModel) {

    var noteList = remember { mutableStateListOf<Note>() }
    val activity = LocalContext.current as AppCompatActivity

//    if (runningFirstTime) { viewModel.onDateChange(LocalDate.now().toString()) }

    viewModel.selectedDate.observe(activity) {
        GlobalScope.launch(Dispatchers.Main) {
            var items = withContext(Dispatchers.IO) {
                viewModel.getFeedingByDate(LocalDate.parse(it))
            }
            noteList.swapList(items)
        }
    }

    val removeNote = { note: Note ->
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) { viewModel.removeNote(note) }
            viewModel.onDateChange(note.date.toString())
        }
    }

    val timeConverter = TimeConverter()
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(noteList) { note ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 6.dp, end = 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            )
            {
                Text(
                    text = "${timeConverter.convertTimeFromLongToString(note.start)}",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = "${timeConverter.convertTimeFromLongToString(note.end)}",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = "${timeConverter.getDisplayValue(note.duration)}",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = "${note.side}",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                TextButton(
                    onClick = { removeNote(note) },
                    modifier = Modifier
                        .padding(2.dp)
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Delete_icon",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }

            }

        }
    }
}


fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}




