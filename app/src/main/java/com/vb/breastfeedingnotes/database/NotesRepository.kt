package com.vb.breastfeedingnotes.database

import androidx.lifecycle.LiveData
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

       fun getFeedingsByDate(selected_date: LocalDate): List<Note> {
       return notesDao.getFeedingsByDate(selected_date)
    }
}