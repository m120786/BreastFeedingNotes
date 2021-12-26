package com.vb.breastfeedingnotes.database

import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

class NotesRepository @Inject constructor(private val notesDao: NotesDao): NotesService {

    @ExperimentalTime
    override suspend fun addNote(note: Note) {
        notesDao.insert(notesEntity = note.toNotesEntity())
    }

    override suspend fun deleteNote(notesEntity: NotesEntity) {
        notesDao.delete(notesEntity)
    }

    override fun getLastFeeding(): Flow<NotesEntity> {
        return notesDao.getLatestFeeding()
    }

    override fun getFeedingsByDate(selected_date: LocalDate): Flow<List<NotesEntity>> {
       return notesDao.getFeedingsByDate(selected_date)
    }

    @ExperimentalTime
    override suspend fun calculateDurationAndSave(date: LocalDate, start: Instant, end: Instant, side: SidePick){
        var duration = start?.until(end, ChronoUnit.MILLIS)?.toDuration(DurationUnit.MILLISECONDS)
                if (duration.isNegative()) {
                    duration = duration.plus(Duration.Companion.days(1))
                }
        notesDao.insert(Note(date,start,end,duration, side).toNotesEntity())
    }

    @ExperimentalTime
    override suspend fun calculateDuration(start: Instant, end: Instant): Duration {
        var duration = start?.until(end, ChronoUnit.MILLIS)?.toDuration(DurationUnit.MILLISECONDS)
        if (duration.isNegative()) {
            duration = duration.plus(Duration.Companion.days(1))
        }
        return duration
    }

}