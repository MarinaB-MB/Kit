package com.deadely.ege.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PointsObject(
    var math: List<Point>,
    var infa: List<Point>,
    var ryss: List<Point>,
    var fiz: List<Point>
) : Parcelable

@Parcelize
data class Point(
    val first_point: String,
    val second_point: Int
) : Parcelable
