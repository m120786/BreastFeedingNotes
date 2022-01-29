package com.vb.breastfeedingnotes.di

import android.content.Context
import com.vb.breastfeedingnotes.database.notes.NotesDao
import com.vb.breastfeedingnotes.database.NotesDatabase
import com.vb.breastfeedingnotes.database.weight.WeightDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getNotesDb(@ApplicationContext context: Context): NotesDatabase {
        return NotesDatabase.createDb(context)
    }

    @Singleton
    @Provides
    fun getNotesDao(notesDB: NotesDatabase): NotesDao {
        return notesDB.getNotesDao()
    }

    @Singleton
    @Provides
    fun getWeightDao(notesDB: NotesDatabase): WeightDao {
        return notesDB.getWeightDao()
    }


}