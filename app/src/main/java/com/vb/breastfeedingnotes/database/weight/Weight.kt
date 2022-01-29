package com.vb.breastfeedingnotes.database.weight

import java.time.LocalDate

data class Weight(
    val id: Int,
    val weighDate: LocalDate,
    val weight: Float
)
