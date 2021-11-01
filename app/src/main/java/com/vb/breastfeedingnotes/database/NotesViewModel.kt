package com.vb.breastfeedingnotes.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.vb.breastfeedingnotes.database.Note
import com.vb.breastfeedingnotes.database.NotesDatabase
import com.vb.breastfeedingnotes.database.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val repository: NotesRepository): ViewModel() {

    private val _start_time = MutableLiveData<String>()
    val start_time: LiveData<String> = _start_time

    private val _end_time = MutableLiveData<String>()
    val end_time: LiveData<String> = _end_time

    private val _side = MutableLiveData<String>()
    val side: LiveData<String> = _side

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> = _selectedDate

    private val _duration_time = MutableLiveData<String>()


    fun onDateChange(date: String?) {
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

    fun removeNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

    fun getLastFeeding(): LiveData<Note> {
        return repository.getLastFeeding()
    }


    fun getFeedingByDate(date: LocalDate): List<Note> {
        return repository.getFeedingsByDate(date)
    }

}