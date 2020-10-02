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
    val point: Double,
) : Parcelable

//////////////////////////////
@Parcelize
data class Disciplines(
    val _id: String,
    val name: String,
    val image: String,
    val variants: List<Variant>,
) : Parcelable

@Parcelize
data class Variant(
    val _id: String,
    val title: String,
    val asks: List<String>,
    val number: Int
) : Parcelable

@Parcelize
data class Asks(
    val _id: String,
    val ask: String,
    val image: String,
    val answer: List<Answer>
) : Parcelable


@Parcelize
data class Answer(
    val _id: String,
    val answer: String,
    val right: Boolean
) : Parcelable