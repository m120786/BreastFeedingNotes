package com.vb.breastfeedingnotes.database

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class NotesRepository @Inject constructor(private val notesDao: NotesDao) {
    val readAllNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    suspend fun addNote(note: Note) {
        notesDao.insert(note)
    }

    suspend fun deleteNote(note: Note) {
        notesDao.delete(note)
    }

    fun getLastFeeding(): LiveData<Note> {
        return notesDao.getLatestFeeding()
    }

    fun getFeedingsByDate(selected_date: LocalDate): Flow<List<Note>> {
       return notesDao.getFeedingsByDate(selected_date)
    }
}