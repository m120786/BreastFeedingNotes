package com.vb.breastfeedingnotes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vb.breastfeedingnotes.ui.theme.Primary
import com.vb.breastfeedingnotes.ui.theme.Grey

import com.vb.breastfeedingnotes.utils.TimeConverter
import com.vb.breastfeedingnotes.viewmodel.NotesViewModel
import com.vb.breastfeedingnotes.R
import com.vb.breastfeedingnotes.database.Note
import com.vb.breastfeedingnotes.ui.theme.PrimaryDark
import com.vb.breastfeedingnotes.ui.theme.Secondary
import java.time.LocalDate

@Composable
fun ShowLastNote() {

    val viewModel: NotesViewModel = viewModel()

    val last_feeding by viewModel.lastNote.collectAsState(initial = Note(0, LocalDate.now(),0,0,0,"L"))
//    val last_feeding = viewModel.getLastFeeding().observeAsState()
    var timeConverter = remember{ TimeConverter() }
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
            Row() {
                Text(
                    text = "Paskutinis maitinimas",
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
                if (last_feeding.id != null) {
                    Text(
                        text = "${timeConverter.convertTimeFromLongToString(last_feeding.start)}",
                        color = Color.Black
                    )
                    Text(
                        text = "${timeConverter.convertTimeFromLongToString(last_feeding.end)}",
                        color = Color.Black
                    )
                    Text(
                        text = "${timeConverter.getDisplayValue(last_feeding.duration)}",
                        color = Color.Black
                    )
                    Text(text = "${last_feeding.side}", color = Color.Black)
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