package com.vb.breastfeedingnotes.di

import com.vb.breastfeedingnotes.database.notes.NotesRepository
import com.vb.breastfeedingnotes.database.notes.NotesService
import com.vb.breastfeedingnotes.database.weight.WeightRepository
import com.vb.breastfeedingnotes.database.weight.WeightService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModuleForInterface {

    @Binds
    abstract fun bindNotesRepository(notesRepository: NotesRepository): NotesService
    @Binds
    abstract fun bindWeightRepository(weightRepository: WeightRepository): WeightService

}


