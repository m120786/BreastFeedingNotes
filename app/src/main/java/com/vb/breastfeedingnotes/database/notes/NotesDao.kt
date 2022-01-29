package com.vb.breastfeedingnotes.database.notes

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notesEntity: NotesEntity)

    @Delete
    suspend fun delete(notesEntity: NotesEntity)

    @Query("SELECT * FROM my_breastfeeding_notes WHERE id=(SELECT max(id) FROM my_breastfeeding_notes)")
    fun getLatestFeeding(): Flow<NotesEntity>

    @Query("SELECT * FROM my_breastfeeding_notes WHERE date LIKE :selected_date ORDER BY start_time DESC")
    fun getFeedingsByDate(selected_date: LocalDate): Flow<List<NotesEntity>>
}