package com.vb.breastfeedingnotes.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vb.breastfeedingnotes.views.notesView.NotesViewModel
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import kotlin.time.ExperimentalTime


@ExperimentalTime
@Composable
fun NotesList() {

    val viewModel = hiltViewModel<NotesViewModel>()
    val noteList by viewModel.notes.collectAsState(initial = emptyList())

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
                    text = "${LocalTime.from(note.startTime.atZone(ZoneId.systemDefault())).truncatedTo(ChronoUnit.MINUTES)}",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = "${LocalTime.from(note.endTime.atZone(ZoneId.systemDefault())).truncatedTo(ChronoUnit.MINUTES)}",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = "${note.duration}",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = "${note.side}",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                TextButton(
                    onClick = {
                            viewModel.removeNote(note)
                    },
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




