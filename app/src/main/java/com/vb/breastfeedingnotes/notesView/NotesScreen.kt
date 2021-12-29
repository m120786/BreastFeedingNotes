package com.vb.breastfeedingnotes.notesView

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vb.breastfeedingnotes.R
import com.vb.breastfeedingnotes.navigation.Screen
import com.vb.breastfeedingnotes.notesView.addNote.AddNote
import com.vb.breastfeedingnotes.notesView.lastNote.ShowLastNote
import com.vb.breastfeedingnotes.ui.DatePickerView
import com.vb.breastfeedingnotes.ui.NotesList
import com.vb.breastfeedingnotes.ui.RowOfIcons
import com.vb.breastfeedingnotes.ui.theme.PrimaryDark
import com.vb.breastfeedingnotes.ui.theme.PrimaryVeryLight
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Composable
fun NotesScreen(
    navController: NavController
) {

    Surface(color = White) {
        Column {
            Row(Modifier.weight(1f)) {
                ShowLastNote()
            }
            Spacer(modifier = Modifier.padding(1.dp))
            Row(Modifier.weight(3f)) {
                Card(
                    elevation = 2.dp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
                ) {
                    Column(horizontalAlignment = Alignment.End) {
                        DatePickerView()
                        RowOfIcons()
                        NotesList()

                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = { navController.navigate(Screen.DialogScreen.route) },
                    modifier = Modifier.background(PrimaryVeryLight),
                    border = BorderStroke(
                        1.dp,
                        PrimaryDark
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_playlist_add_24),
                        contentDescription = "add_note_button",
                        tint = Color.Black,
                    )
                }
            }
            Row(Modifier.weight(2f)) {
                AddNote()
            }
        }
    }
}