package com.vb.breastfeedingnotes.ui

import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vb.breastfeedingnotes.database.toNote
import com.vb.breastfeedingnotes.utils.TimeConverter
import com.vb.breastfeedingnotes.notesView.NotesViewModel
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import kotlin.time.ExperimentalTime


@ExperimentalTime
@Composable
fun NotesList(viewModel: NotesViewModel) {

    val noteList by viewModel.notes.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(noteList) { note ->
            var notes = note.toNote()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 6.dp, end = 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            )
            {
                Text(
                    text = "${LocalTime.from(notes.startTime.atZone(ZoneId.systemDefault())).truncatedTo(ChronoUnit.MINUTES)}",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = "${LocalTime.from(notes.endTime.atZone(ZoneId.systemDefault())).truncatedTo(ChronoUnit.MINUTES)}",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = "${notes.duration}",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = "${notes.side}",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                val scope = rememberCoroutineScope()
                TextButton(
                    onClick = {
                        scope.launch {
                        viewModel.removeNote(note)
                        } },
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




