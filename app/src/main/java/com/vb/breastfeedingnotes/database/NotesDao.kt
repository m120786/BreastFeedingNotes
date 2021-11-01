package com.vb.breastfeedingnotes.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.time.LocalDate
import java.util.*

@Dao
interface NotesDao {

    @Query("SELECT * FROM my_breastfeeding_notes")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM my_breastfeeding_notes WHERE id=(SELECT max(id) FROM my_breastfeeding_notes)")
    fun getLatestFeeding(): LiveData<Note>

    @Query("SELECT * FROM my_breastfeeding_notes WHERE date LIKE :selected_date ORDER BY start_time DESC")
     fun getFeedingsByDate(selected_date: LocalDate): List<Note>
}