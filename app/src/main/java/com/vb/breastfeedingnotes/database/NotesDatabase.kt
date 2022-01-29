package com.vb.breastfeedingnotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vb.breastfeedingnotes.database.notes.NotesDao
import com.vb.breastfeedingnotes.database.notes.NotesEntity
import com.vb.breastfeedingnotes.database.weight.WeightDao
import com.vb.breastfeedingnotes.database.weight.WeightEntity
import com.vb.breastfeedingnotes.utils.DateConverter

@Database(version = 3, entities = [WeightEntity::class, NotesEntity::class],  exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao
    abstract fun getWeightDao(): WeightDao

    companion object {
        fun createDb(context: Context): NotesDatabase = Room.databaseBuilder(
            context.applicationContext,
            NotesDatabase::class.java,
            "notes_Database",
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}