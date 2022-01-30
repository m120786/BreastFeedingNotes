package com.vb.breastfeedingnotes.database.notes

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

class NotesRepository @Inject constructor(private val notesDao: NotesDao): NotesService {

    @OptIn(ExperimentalTime::class)
    override suspend fun addNote(note: Note) {
        notesDao.insert(notesEntity = note.toNotesEntity())
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun deleteNote(note: Note) {
        notesDao.delete(note.toNotesEntity())
    }

    @OptIn(ExperimentalTime::class)
    override fun getLastFeeding(): Flow<Note> {
        return notesDao.getLatestFeeding().map { it.toNote() }
    }

    @OptIn(ExperimentalTime::class)
    override fun getFeedingsByDate(selected_date: LocalDate): Flow<List<Note>> {
       return notesDao.getFeedingsByDate(selected_date).map { list -> list.map {it.toNote()} }
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun calculateDurationAndSave(date: LocalDate, start: Instant, end: Instant, side: SidePick){
        var duration = start.until(end, ChronoUnit.MILLIS).toDuration(DurationUnit.MILLISECONDS)
                if (duration.isNegative()) {
                    duration = duration.plus(Duration.Companion.days(1))
                }
        notesDao.insert(Note(0, date,start,end,duration, side).toNotesEntity())
    }

    @ExperimentalTime
    override suspend fun calculateDuration(start: Instant, end: Instant): Duration {
        var duration = start.until(end, ChronoUnit.MILLIS).toDuration(DurationUnit.MILLISECONDS)
        if (duration.isNegative()) {
            duration = duration.plus(Duration.Companion.days(1))
        }
        return duration
    }

}