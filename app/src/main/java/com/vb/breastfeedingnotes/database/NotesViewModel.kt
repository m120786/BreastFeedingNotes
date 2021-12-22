package com.vb.breastfeedingnotes.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.vb.breastfeedingnotes.asFlow
import com.vb.breastfeedingnotes.database.Note
import com.vb.breastfeedingnotes.database.NotesDatabase
import com.vb.breastfeedingnotes.database.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val repository: NotesRepository): ViewModel() {

    private val _start_time = MutableStateFlow<String>("0")
    val start_time: Flow<String> = _start_time

    private val _end_time = MutableStateFlow<String>("0")
    val end_time: Flow<String> = _end_time

    private val _side = MutableStateFlow<String>("L")
    val side: Flow<String> = _side

    private val _selectedDate = MutableStateFlow<String>(LocalDate.now().toString())
    val selectedDate: Flow<String> = _selectedDate

    private val _duration_time = MutableStateFlow<String>("0")

    val notes = selectedDate.flatMapLatest { repository.getFeedingsByDate(LocalDate.parse(it)) }

//    val notes = selectedDate.asFlow().flatMapLatest {  repository.getFeedingsByDate(LocalDate.parse(it)) }

    val lastNote = repository.getLastFeeding()


    fun onDateChange(date: String) {
        _selectedDate.value = date
    }


    fun onStartTimeChange(start: String) {
        _start_time.value = start
    }

    fun onEndTimeChange(end: String) {
        _end_time.value = end
    }

    fun onDurationTimeChange(duration: String) {
        _duration_time.value = duration
    }

    fun onSideChange(chosen_side: String) {
        _side.value = chosen_side
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
        }
    }

   suspend fun removeNote(note: Note) {
            repository.deleteNote(note)

    }


//    fun getFeedingByDate(date: LocalDate): List<Note> {
//        return repository.getFeedingsByDate(date)
//    }

}