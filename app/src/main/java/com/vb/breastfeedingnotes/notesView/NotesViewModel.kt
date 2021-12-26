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

    private val _selectedDate = MutableStateFlow<LocalDate>(LocalDate.now())
    val selectedDate: Flow<LocalDate> = _selectedDate

    private val _startTime = MutableStateFlow<Instant>(Instant.now())
    val startTime: Flow<Instant> = _startTime

     fun getStartTime() {
        _startTime.value = Instant.now()
         _selectedDate.value = LocalDate.now()
    }

    private val _endTime = MutableStateFlow<Instant>(Instant.now())

    private val _side = MutableStateFlow<SidePick>(SidePick.Left)
    val side: Flow<SidePick> = _side

    fun getDurationAndSave() {
        _endTime.value = Instant.now()
        viewModelScope.launch {
            notesService.calculateDurationAndSave(_selectedDate.value, _startTime.value, _endTime.value, _side.value)
        }
    }

    val notes = selectedDate.flatMapLatest { notesService.getFeedingsByDate(it) }

    val lastNote by lazy { notesService.getLastFeeding() }


    fun setDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun setSide(chosen_side: SidePick) {
        _side.value = chosen_side
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            notesService.addNote(note)
        }
    }

    suspend fun removeNote(notesEntity: NotesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            notesService.deleteNote(notesEntity)
        }
    }
}