package com.vb.breastfeedingnotes.database.notes

import java.time.Instant
import java.time.LocalDate
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

data class Note @ExperimentalTime constructor(
    val id: Int,
    val date: LocalDate,
    val startTime: Instant,
    val endTime: Instant,
    val duration: Duration,
    val side: SidePick
)

enum class SidePick{Left, Right}
