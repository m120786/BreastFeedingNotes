package com.vb.breastfeedingnotes.database.weight

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeight(weightEntity: WeightEntity)

    @Delete
    suspend fun deleteWeight(weightEntity: WeightEntity)

    @Query("SELECT * FROM baby_weight ORDER BY date_of_weigh ASC")
    fun getAllWeight(): Flow<List<WeightEntity>>
}