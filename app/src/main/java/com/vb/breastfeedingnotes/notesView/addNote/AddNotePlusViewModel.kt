package com.vb.breastfeedingnotes.notesView.addNote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vb.breastfeedingnotes.database.Note
import com.vb.breastfeedingnotes.database.NotesService
import com.vb.breastfeedingnotes.database.SidePick
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@HiltViewModel
class AddNotePlusViewModel @Inject constructor(private val notesService: NotesService): ViewModel() {

    private val _startTimeDialog = MutableStateFlow<Instant>(Instant.now())
    val startTimeDialog: Flow<Instant> = _startTimeDialog

    fun setStartTimeDialog(startT: Instant) {
        _startTimeDialog.value = startT
    }

    private val _endTimeDialog = MutableStateFlow<Instant>(Instant.now())
    val endTimeDialog: Flow<Instant> = _endTimeDialog

    fun setEndTimeDialog(endT: Instant) {
        _endTimeDialog.value = endT
    }

    @ExperimentalTime
    private val _duration = MutableStateFlow(Duration.ZERO)
    @ExperimentalTime
    val duration: Flow<Duration> = _duration

    private val _sideDialog = MutableStateFlow(SidePick.Left)
    val sideDialog: Flow<SidePick> = _sideDialog

    fun setSide(chosen_side: SidePick) {
        _sideDialog.value = chosen_side
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            notesService.addNote(note)
        }
    }

    @ExperimentalTime
    fun calculateDuration() {
        viewModelScope.launch {
            _duration.value = notesService.calculateDuration(_startTimeDialog.value, _endTimeDialog.value)
        }
    }
    @ExperimentalTime
    fun saveObj() {
        viewModelScope.launch {
            notesService.calculateDurationAndSave(LocalDate.now(),_startTimeDialog.value, _endTimeDialog.value, _sideDialog.value)
        }
    }


}