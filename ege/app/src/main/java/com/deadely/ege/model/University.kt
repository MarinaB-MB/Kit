package com.deadely.ege.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class University(
    val _id: String,
    val image: String,
    val name: String,
    val site: String,
    val specialities: List<Speciality>
) : Parcelable

@Parcelize
data class Speciality(
    val _id: String,
    val name: String,
    val point: Double
) : Parcelable
