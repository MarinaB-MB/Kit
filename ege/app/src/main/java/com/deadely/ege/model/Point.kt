package com.deadely.ege.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Point(
    val _id: String,
    val first_point: String,
    val second_point: Int
) : Parcelable
