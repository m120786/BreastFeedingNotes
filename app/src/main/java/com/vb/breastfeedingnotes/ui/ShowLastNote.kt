package com.vb.breastfeedingnotes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vb.breastfeedingnotes.ui.theme.Primary
import com.vb.breastfeedingnotes.ui.theme.Grey

import com.vb.breastfeedingnotes.utils.TimeConverter
import com.vb.breastfeedingnotes.viewmodel.NotesViewModel
import com.vb.breastfeedingnotes.R
import com.vb.breastfeedingnotes.ui.theme.PrimaryDark
import com.vb.breastfeedingnotes.ui.theme.Secondary

@Composable
fun ShowLastNote(viewModel: NotesViewModel) {
    val last_feeding = viewModel.getLastFeeding().observeAsState()
    val timeConverter = TimeConverter()
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
                if (last_feeding.value?.id != null) {
                    Text(
                        text = "${timeConverter.convertTimeFromLongToString(last_feeding.value?.start)}",
                        color = Color.Black
                    )
                    Text(
                        text = "${timeConverter.convertTimeFromLongToString(last_feeding.value?.end)}",
                        color = Color.Black
                    )
                    Text(
                        text = "${timeConverter.getDisplayValue(last_feeding.value?.duration)}",
                        color = Color.Black
                    )
                    Text(text = "${last_feeding.value?.side}", color = Color.Black)
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