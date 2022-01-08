package com.vb.breastfeedingnotes.notesView.lastNote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vb.breastfeedingnotes.R
import com.vb.breastfeedingnotes.database.Note
import com.vb.breastfeedingnotes.database.NotesEntity
import com.vb.breastfeedingnotes.database.SidePick
import com.vb.breastfeedingnotes.database.toNote
import com.vb.breastfeedingnotes.notesView.NotesViewModel
import com.vb.breastfeedingnotes.ui.theme.PrimaryDark
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Composable
fun ShowLastNote() {

    val viewModel = hiltViewModel<NotesViewModel>()

    

    val lastFeeding by viewModel.lastNote.collectAsState(initial = Note(0, LocalDate.now(), Instant.now(),Instant.now(), Duration.ZERO,SidePick.Left))

    Card(elevation = 2.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp)) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    text = stringResource(id = R.string.last_feed),
                    style = MaterialTheme.typography.h6,
                    color = PrimaryDark
                )
                Spacer(modifier = Modifier.padding(6.dp))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (lastFeeding != null) {
                    Text(
                        text = "${LocalTime.from(lastFeeding.startTime.atZone(ZoneId.systemDefault())).truncatedTo(ChronoUnit.MINUTES)}",
                        color = Color.Black
                    )
                    Text(
                        text = "${LocalTime.from(lastFeeding.endTime.atZone(ZoneId.systemDefault())).truncatedTo(ChronoUnit.MINUTES)}",
                        color = Color.Black
                    )
                    Text(
                        text = "${lastFeeding.duration}",
                        color = Color.Black
                    )
                    Text(text = "${lastFeeding.side}", color = Color.Black)
                } else {
                    Text(text = "----", color = Color.Black)
                    Text(text = "----", color = Color.Black)
                    Text(text = "----", color = Color.Black)
                    Text(text = "----", color = Color.Black)
                }
            }
        }
    }
}