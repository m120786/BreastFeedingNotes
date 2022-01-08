package com.vb.breastfeedingnotes.notesView

import androidx.lifecycle.*
import com.vb.breastfeedingnotes.database.Note
import com.vb.breastfeedingnotes.database.NotesEntity
import com.vb.breastfeedingnotes.database.NotesService
import com.vb.breastfeedingnotes.database.SidePick
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@ExperimentalTime
@HiltViewModel
class NotesViewModel @Inject constructor(private val notesService: NotesService): ViewModel() {

     val selectedDate = MutableStateFlow<LocalDate>(LocalDate.now())

     val startTime = MutableStateFlow<Instant>(Instant.now())

     val endTime = MutableStateFlow<Instant>(Instant.now())

     val side = MutableStateFlow(SidePick.Left)

    fun getStartTime() {
        startTime.value = Instant.now()
        selectedDate.value = LocalDate.now()
    }

    fun getDurationAndSave() {
        endTime.value = Instant.now()
        viewModelScope.launch {
            notesService.calculateDurationAndSave(selectedDate.value, startTime.value, endTime.value, side.value)
        }
    }

    val notes = selectedDate.flatMapLatest { findFeedingByDate(it) }

    val lastNote by lazy { notesService.getLastFeeding() }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notesService.addNote(note)
        }
    }

     fun removeNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notesService.deleteNote(note)
        }
    }
    fun findFeedingByDate(date: LocalDate): Flow<List<Note>> {
        return notesService.getFeedingsByDate(date)
    }

}