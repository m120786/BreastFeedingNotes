package com.vb.breastfeedingnotes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalDate
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@Entity(tableName = "my_breastfeeding_notes")
data class NotesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "start_time") val start: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "end_time") val end: Long = start,
    @ColumnInfo(name = "duration") val duration: Long,
    @ColumnInfo(name = "side") val side: String
)

@ExperimentalTime
fun NotesEntity.toNote(): Note {
    return Note(
        date = LocalDate.parse(date.toString()),
        startTime = Instant.ofEpochMilli(start),
        endTime = Instant.ofEpochMilli(end),
        duration = duration.toDuration(TimeUnit.MINUTES),
        side = SidePick.valueOf(side)
    )
}
@ExperimentalTime
fun Note.toNotesEntity(): NotesEntity {
    return NotesEntity(
        id = 0,
        date = date,
        start = startTime.toEpochMilli(),
        end = endTime.toEpochMilli(),
        duration = duration.toLong(TimeUnit.MINUTES),
        side = side.name
    )
}