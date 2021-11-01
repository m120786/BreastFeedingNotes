package com.vb.breastfeedingnotes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "my_breastfeeding_notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "start_time") val start: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "end_time") val end: Long = start,
    @ColumnInfo(name = "duration") val duration: Long,
    @ColumnInfo(name = "side") val side: String
) {

}