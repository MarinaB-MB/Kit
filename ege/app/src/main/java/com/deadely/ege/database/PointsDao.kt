package com.deadely.ege.database

import androidx.room.*

@Dao
interface PointsDao {
    @Query("SELECT * FROM points_table")
    suspend fun getPoints(): List<PointEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllToPoints(pointEntity: PointEntity)

    @Delete
    suspend fun deletePoints(pointEntity: List<PointEntity>)
}