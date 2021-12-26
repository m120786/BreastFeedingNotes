package com.vb.breastfeedingnotes.database

import kotlinx.coroutines.flow.Flow
import java.time.Instant
import kotlin.time.Duration
import java.time.LocalDate
import kotlin.time.ExperimentalTime

interface NotesService {

    suspend fun addNote(note: Note): Unit

    suspend fun deleteNote(notesEntity: NotesEntity): Unit

    fun getLastFeeding(): Flow<NotesEntity>

    fun getFeedingsByDate(selected_date: LocalDate): Flow<List<NotesEntity>>

    @ExperimentalTime
    suspend fun calculateDurationAndSave(date: LocalDate, start: Instant, end: Instant, side: SidePick)

    @ExperimentalTime
    suspend fun calculateDuration(start: Instant, end: Instant): Duration

}