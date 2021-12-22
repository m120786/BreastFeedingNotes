package com.vb.breastfeedingnotes

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vb.breastfeedingnotes.ui.*
import com.vb.breastfeedingnotes.ui.theme.BreastFeedingNotesTheme
import com.vb.breastfeedingnotes.ui.theme.White
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BreastFeedingNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = White) {
                    Column {
                        Row( Modifier.weight(1f)) {
                            ShowLastNote()
                        }
                        Spacer(modifier = Modifier.padding(1.dp))
                        Row( Modifier.weight(4f)) {
                            Card(elevation = 2.dp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                                Column(horizontalAlignment = Alignment.End) {
                                        DatePickerView()
                                        RowOfIcons()
                                        NotesList()

                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(1.dp))
                        Row( Modifier.weight(3f)) {
                            AddNoteScreen()
                        }
                    }
                }
            }
        }
    }
}