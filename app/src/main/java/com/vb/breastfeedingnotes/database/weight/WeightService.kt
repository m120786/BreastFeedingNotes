package com.vb.breastfeedingnotes.database.weight

import kotlinx.coroutines.flow.Flow

interface WeightService {
    suspend fun insert(weight: Weight)

    suspend fun delete(weight: Weight)

    fun getAllWeight(): Flow<List<Weight>>
}