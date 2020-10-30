package com.deadely.ege.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.core.content.ContextCompat
import com.deadely.ege.base.App

object Utils {

    @JvmStatic
    fun getString(id: Int): String {
        return App.instance.resources.getString(id)
    }

    @JvmStatic
    fun getColor(id: Int): Int {
        return ContextCompat.getColor(App.instance.applicationContext, id)
    }

    @JvmStatic
    fun decodeString(imageString: String): Bitmap {
        val decodedString: ByteArray = Base64.decode(imageString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    @JvmStatic
    fun getMiddleValue(list: List<Int?>): Int {
        var middleCount = 0
        for (item in list) {
            item?.let { middleCount += it }
        }
        middleCount /= list.size
        return middleCount
    }

}
