package com.vb.breastfeedingnotes.database.weight

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeightRepository @Inject constructor(private val weightDao: WeightDao): WeightService {
    override suspend fun insert(weight: Weight) {
        weightDao.insertWeight(weight.toWeightEntity())
    }

    override suspend fun delete(weight: Weight) {
        weightDao.deleteWeight(weight.toWeightEntity())
    }

    override fun getAllWeight(): Flow<List<Weight>> {
        return weightDao.getAllWeight().map { value -> value.map { it.toWeight() } }
    }
}