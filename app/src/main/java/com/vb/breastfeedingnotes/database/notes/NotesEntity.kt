package com.vb.breastfeedingnotes.database.notes

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
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "date") val date: LocalDate = LocalDate.now(),
    @ColumnInfo(name = "start_time") val start: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "end_time") val end: Long = start,
    @ColumnInfo(name = "duration") val duration: Long = 0L,
    @ColumnInfo(name = "side") val side: String = "left"
)

@ExperimentalTime
fun NotesEntity.toNote(): Note {
    return Note(
        id = id,
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
        id = id,
        date = date,
        start = startTime.toEpochMilli(),
        end = endTime.toEpochMilli(),
        duration = duration.toLong(TimeUnit.MINUTES),
        side = side.name
    )
}