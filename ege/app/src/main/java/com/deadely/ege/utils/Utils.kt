package com.deadely.ege.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View


fun View.makeGone() {
    visibility = View.GONE
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun decodeString(imageString: String): Bitmap {
    val decodedString: ByteArray = Base64.decode(imageString, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
}