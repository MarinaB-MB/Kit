package com.deadely.ege.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Disciplines(
    val _id: String,
    val name: String,
    val image: String,
    val variants: List<Variant>,
    val eid: String,
    val max_points: Int,
) : Parcelable

@Parcelize
data class Variant(
    val _id: String,
    val title: String,
    val asks: List<String>,
    val number: Int,
    val eid: String
) : Parcelable

@Parcelize
data class Asks(
    val _id: String = "",
    val ask: String = "",
    val image: String = "",
    val is_svg: Boolean = false,
    val number: Int = -1,
    val answer: List<Answer> = arrayListOf()
) : Parcelable


@Parcelize
data class Answer(
    val _id: String,
    val answer: String,
    val right: Boolean
) : Parcelable