package com.vb.breastfeedingnotes.di

import android.app.Application
import android.content.Context
import com.vb.breastfeedingnotes.database.NotesDao
import com.vb.breastfeedingnotes.database.NotesDatabase
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
    fun getDao(notesDB: NotesDatabase): NotesDao {
        return notesDB.getDao()
    }


}