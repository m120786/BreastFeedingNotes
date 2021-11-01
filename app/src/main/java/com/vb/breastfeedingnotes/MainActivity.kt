package com.vb.breastfeedingnotes

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.vb.breastfeedingnotes.ui.*
import com.vb.breastfeedingnotes.ui.theme.BreastFeedingNotesTheme
import com.vb.breastfeedingnotes.ui.theme.Grey2
import com.vb.breastfeedingnotes.ui.theme.White
import com.vb.breastfeedingnotes.ui.timer.TimerViewModel
import com.vb.breastfeedingnotes.viewmodel.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lateinit var mNotesViewModel: NotesViewModel
        mNotesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)



        val mTimerViewModel: TimerViewModel by viewModels()
        setContent {
            BreastFeedingNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = White) {
                    Column {
                        Row( Modifier.weight(1f)) {
                            ShowLastNote(viewModel = mNotesViewModel)
                        }
                        Spacer(modifier = Modifier.padding(1.dp))
                        Row( Modifier.weight(4f)) {
                            Card(elevation = 2.dp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                                Column(horizontalAlignment = Alignment.End) {
                                        DatePickerView(mNotesViewModel)
                                        AddNotePlus()
                                        RowOfIcons()
                                        NotesList(mNotesViewModel)

                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(1.dp))
                        Row( Modifier.weight(3f)) {
                            AddNoteScreen(mNotesViewModel, mTimerViewModel)
                        }
                    }
                }
            }
        }
    }
}