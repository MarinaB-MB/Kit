package com.deadely.ege.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Disciplines(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("variants") val variants: List<Variant>,
    @SerializedName("eid") val eid: String,
    @SerializedName("max_points") val maxPoints: Int,
) : Parcelable

@Parcelize
data class Variant(
    @SerializedName("_id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("asks") val asks: List<String>,
    @SerializedName("number") val number: Int,
    @SerializedName("eid") val eid: String
) : Parcelable

@Parcelize
data class Asks(
    @SerializedName("_id") val id: String = "",
    @SerializedName("ask") val ask: String = "",
    @SerializedName("image") val image: String = "",
    @SerializedName("is_svg") val isSvg: Boolean = false,
    @SerializedName("number") val number: Int = -1,
    @SerializedName("answer") val answer: List<Answer> = arrayListOf()
) : Parcelable


@Parcelize
data class Answer(
    @SerializedName("_id") val id: String,
    @SerializedName("answer") val answer: String,
    @SerializedName("right") val right: Boolean
) : Parcelable