package com.vb.breastfeedingnotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vb.breastfeedingnotes.utils.DateConverter

@Database(entities = [NotesEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getDao(): NotesDao

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