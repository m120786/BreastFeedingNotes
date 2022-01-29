package com.vb.breastfeedingnotes.database.weight

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset

@Entity(tableName = "baby_weight")
data class WeightEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "date_of_weigh") val entityWeighDate: Long,
    @ColumnInfo(name = "weight") val entityWeight: Float
)

fun WeightEntity.toWeight(): Weight {
return Weight(
    id = id,
    weighDate = Instant.ofEpochMilli(entityWeighDate).atZone(ZoneId.systemDefault()).toLocalDate(),
    weight = entityWeight
)
}

fun Weight.toWeightEntity(): WeightEntity {
    return WeightEntity(
        id = id,
        entityWeighDate = weighDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli(),
        entityWeight = weight
    )
}