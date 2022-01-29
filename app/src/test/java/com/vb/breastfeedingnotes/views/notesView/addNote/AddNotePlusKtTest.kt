package com.vb.breastfeedingnotes.views.notesView.addNote

import ch.tutteli.atrium.api.fluent.en_GB.toBeEqualComparingTo
import ch.tutteli.atrium.api.verbs.expect
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId


internal class AddNotePlusKtTest {

    @Test
    fun `test time string conversion to instant when hours less than 10`() {
        val hour = 5
        val minute = 10
        val result = convertToInstant(hour, minute)

        val timeString = "05:10"
        val time = LocalTime.parse(timeString)
        val ldt = time.atDate(LocalDate.now())
        val myInstant = ldt.atZone(ZoneId.systemDefault()).toInstant()
        expect(result).toBeEqualComparingTo(myInstant)
    }

    @Test
    fun `test time string conversion to instant when minutes less than 10`() {
        val hour = 21
        val minute = 8
        val result = convertToInstant(hour, minute)

        val timeString = "21:08"
        val time = LocalTime.parse(timeString)
        val ldt = time.atDate(LocalDate.now())
        val myInstant = ldt.atZone(ZoneId.systemDefault()).toInstant()
        expect(result).toBeEqualComparingTo(myInstant)
    }

    @Test
    fun `test time string conversion to instant when minutes and hours less than 10`() {
        val hour = 1
        val minute = 1
        val result = convertToInstant(hour, minute)

        val timeString = "01:01"
        val time = LocalTime.parse(timeString)
        val ldt = time.atDate(LocalDate.now())
        val myInstant = ldt.atZone(ZoneId.systemDefault()).toInstant()
        expect(result).toBeEqualComparingTo(myInstant)
    }

    @Test
    fun `test time string conversion to instant at midnight`() {
        val hour = 0
        val minute = 0
        val result = convertToInstant(hour, minute)

        val timeString = "00:00"
        val time = LocalTime.parse(timeString)
        val ldt = time.atDate(LocalDate.now())
        val myInstant = ldt.atZone(ZoneId.systemDefault()).toInstant()
        expect(result).toBeEqualComparingTo(myInstant)
    }

}