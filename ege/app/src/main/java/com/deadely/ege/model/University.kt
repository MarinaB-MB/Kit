package com.deadely.ege.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class University(
    @SerializedName("_id") val id: String,
    @SerializedName("image") val image: String,
    @SerializedName("name") val name: String,
    @SerializedName("site") val site: String,
    @SerializedName("specialities") val specialities: List<Speciality>
) : Parcelable

@Parcelize
data class Speciality(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("point") val point: Double
) : Parcelable
