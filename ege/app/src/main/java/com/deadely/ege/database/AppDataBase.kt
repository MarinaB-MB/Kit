package com.deadely.ege.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [MathPointEntity::class, PointEntity::class, InfaPointEntity::class, RyssPointEntity::class, FizPointEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    InfaPointEntityTypeConverter::class,
    MathPointEntityTypeConverter::class,
    RysskiuPointEntityTypeConverter::class,
    FizikaPointEntityTypeConverter::class
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun pointsDao(): PointsDao

    companion object {
        const val DATABASE_NAME: String = "ege_kit"
    }
}