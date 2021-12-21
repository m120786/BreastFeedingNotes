package com.vb.breastfeedingnotes.ui


import com.google.common.truth.Truth.assertThat
import org.hamcrest.MatcherAssert
import org.junit.Test

class DatePickerKtTest {

    @Test
    fun `test if function returns proper date` () {
        var result = DateFormater(10254464)
        assertThat(result).isNotEmpty()
    }
}