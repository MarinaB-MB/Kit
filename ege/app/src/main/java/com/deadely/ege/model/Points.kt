package com.deadely.ege.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PointsObject(
    @SerializedName("math") var math: List<Point>,
    @SerializedName("infa") var infa: List<Point>,
    @SerializedName("ryss") var ryss: List<Point>,
    @SerializedName("fiz") var fiz: List<Point>
) : Parcelable

@Parcelize
data class Point(
    @SerializedName("first_point") val firstPoint: String,
    @SerializedName("second_point") val secondPoint: Int
) : Parcelable
