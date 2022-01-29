package com.vb.breastfeedingnotes.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.vb.breastfeedingnotes.database.notes.NotesDao
import com.vb.breastfeedingnotes.database.notes.NotesEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate


@RunWith(AndroidJUnit4::class)
@SmallTest
class NotesDaoTest2 {
    private lateinit var database: NotesDatabase
    private lateinit var dao: NotesDao


//

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NotesDatabase::class.java
        ).build()

        dao = database.getNotesDao()
    }

    @After
    fun teardown() {
        database.close()

    }

    /*
    Test if it inserts a single note
     */
    @Test
    fun insertNote1() = runBlocking {
        val date = LocalDate.now()
        val notesEntity = NotesEntity(1, date, 112400L, 124122L, 101313L, "Left")
        dao.insert(notesEntity = notesEntity)
        val lastNote = dao.getFeedingsByDate(date).first()
        assertThat(lastNote).contains(notesEntity)
    }

/*
Several values in DB
test inserting several values and displaying latest
 */

    @Test
    fun latestNote() = runBlocking {
        val date = LocalDate.now()
        val notesList = listOf(
            NotesEntity(1, date, 112400L, 124122L, 101313L, "Left"),
            NotesEntity(2, date, 515311L, 4543645L, 121312L, "Right"),
            NotesEntity(3, date, 54545L, 2524524L, 342523L, "Left")
        )

        for (note in notesList) {
            dao.insert(notesEntity = note)
        }
        val lastNote = dao.getLatestFeeding().first()
        assertThat(lastNote).isEqualTo(notesList[2])
    }

    /*

 */

    @Test
    fun getFeedingsByDate() = runBlocking {
        val date = LocalDate.now()
        val noteAdded = NotesEntity(1, LocalDate.now(), 54545L, 2524524L, 342523L, "Left")

            dao.insert(notesEntity = noteAdded)

        val notesByDate = dao.getFeedingsByDate(date).first()
        assertThat(notesByDate).contains(noteAdded)
    }


    @Test
    fun deleteNoteheck() = runBlocking {
        val date = LocalDate.now()
        val noteAdded = NotesEntity(1, LocalDate.now(), 54545L, 2524524L, 342523L, "Left")

        dao.insert(notesEntity = noteAdded)
        dao.delete(noteAdded)

        val notesByDate = dao.getFeedingsByDate(date).first()
        assertThat(notesByDate).doesNotContain(noteAdded)
    }
}



