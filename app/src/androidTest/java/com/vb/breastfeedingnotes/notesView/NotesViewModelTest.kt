package com.vb.breastfeedingnotes.notesView

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.vb.breastfeedingnotes.database.Note
import com.vb.breastfeedingnotes.database.NotesService
import com.vb.breastfeedingnotes.database.SidePick
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Instant
import java.time.LocalDate
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@RunWith(AndroidJUnit4::class)
class NotesViewModelTest {

    private val notesService = mockk<NotesService>()
    @OptIn(ExperimentalTime::class)
    private lateinit var viewModel: NotesViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalTime
    @Before
    fun setup() {
        viewModel = NotesViewModel(notesService)
    }


    @OptIn(ExperimentalTime::class)
    @Test
    fun getFeedingsByDate() {
        val noteList = listOf (
            Note(1, LocalDate.now(), Instant.now(), Instant.now(), Duration.ZERO, SidePick.Right),
            Note(2, LocalDate.now(), Instant.now(), Instant.now(), Duration.ZERO, SidePick.Left),
            Note(3, LocalDate.now(), Instant.now(), Instant.now(), Duration.ZERO, SidePick.Left))

        coEvery { notesService.getFeedingsByDate(any()).take(3).single()} returns noteList

        assertThat(notesService.getFeedingsByDate(LocalDate.now())).isEqualTo(noteList)
    }


}