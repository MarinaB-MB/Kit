package com.deadely.ege.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "points_table")
data class PointEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "point_id")
    val id: Int,
    val math: List<MathPointEntity>,
    val infa: List<InfaPointEntity>,
    val ryss: List<RyssPointEntity>,
    val fiz: List<FizPointEntity>
) : Parcelable


@Parcelize
@Entity(tableName = "matematika_table")
data class MathPointEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "matematika_second_point")
    val first_point: String,
    val second_point: Int
) : Parcelable, PointEntityOverClass()

@Parcelize
@Entity(tableName = "informatika_table")
data class InfaPointEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "informatika_second_point")
    val first_point: String,
    val second_point: Int
) : Parcelable, PointEntityOverClass()

@Parcelize
@Entity(tableName = "rysskiu_table")
data class RyssPointEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "rysskiu_second_point")
    val first_point: String,
    val second_point: Int
) : Parcelable, PointEntityOverClass()

@Parcelize
@Entity(tableName = "fizika_table")
data class FizPointEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "fizika_second_point")
    val first_point: String,
    val second_point: Int
) : Parcelable, PointEntityOverClass()

open class PointEntityOverClass