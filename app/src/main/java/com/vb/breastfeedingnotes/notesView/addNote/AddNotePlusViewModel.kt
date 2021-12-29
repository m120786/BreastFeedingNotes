package com.vb.breastfeedingnotes.notesView.addNote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vb.breastfeedingnotes.database.Note
import com.vb.breastfeedingnotes.database.NotesService
import com.vb.breastfeedingnotes.database.SidePick
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@HiltViewModel
class AddNotePlusViewModel @Inject constructor(private val notesService: NotesService) : ViewModel() {

    val startTime = MutableStateFlow<Instant>(Instant.now())

    val endTime = MutableStateFlow<Instant>(Instant.now())

    @OptIn(ExperimentalTime::class)
    val duration = MutableStateFlow(Duration.ZERO)

    val sideDialog = MutableStateFlow(SidePick.Left)


    fun addNote(note: Note) {
        viewModelScope.launch {
            notesService.addNote(note)
        }
    }

    @ExperimentalTime
    fun calculateDuration() {
        viewModelScope.launch {
            duration.value = notesService.calculateDuration(startTime.value, endTime.value)
        }
    }

    @ExperimentalTime
    fun saveObj() {
        viewModelScope.launch {
            notesService.calculateDurationAndSave(LocalDate.now(), startTime.value, endTime.value, sideDialog.value)
        }
    }


}