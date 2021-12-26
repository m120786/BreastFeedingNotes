package com.vb.breastfeedingnotes.di

import com.vb.breastfeedingnotes.database.NotesRepository
import com.vb.breastfeedingnotes.database.NotesService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModuleForInterface {

    @Binds
    abstract fun bindRepository(repository: NotesRepository): NotesService
}


