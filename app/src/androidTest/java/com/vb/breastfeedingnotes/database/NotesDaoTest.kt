package com.vb.breastfeedingnotes.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.vb.breastfeedingnotes.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class NotesDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: NotesDatabase
    private lateinit var dao: NotesDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NotesDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertNote() = runBlockingTest {
        var date = LocalDate.parse("2021-11-10")
        var note = Note(1, date,10000L,110000L,120000L,"L")
        dao.insert(note)

        val notes = dao.getAllNotes().getOrAwaitValue()

        assertThat(notes).contains(note)
    }

    @Test
    fun deleteNote() = runBlockingTest {
        var date = LocalDate.parse("2021-11-10")
        var note = Note(1, date,10000L,110000L,120000L,"L")
        dao.insert(note)
        dao.delete(note)

        val notes = dao.getAllNotes().getOrAwaitValue()
        assertThat(notes).doesNotContain(note)
    }

    @Test
    fun getFeedingsByDate() = runBlockingTest {
        var date = LocalDate.parse("2021-11-10")
        var note = Note(1, date,10000L,110000L,120000L,"L")
        dao.insert(note)
        var note2 = dao.getFeedingsByDate(date)
        assertThat(note2).contains(note)
    }

}