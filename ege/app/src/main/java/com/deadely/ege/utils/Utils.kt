package com.deadely.ege.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import androidx.core.content.ContextCompat
import com.deadely.ege.base.App


fun View.makeGone() {
    visibility = View.GONE
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

class Utils {
    companion object {

        fun getString(id: Int): String {
            return App.instance.resources.getString(id)
        }

        fun getColor(id: Int): Int {
            return ContextCompat.getColor(App.instance.applicationContext, id)
        }

        fun decodeString(imageString: String): Bitmap {
            val decodedString: ByteArray = Base64.decode(imageString, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        }

        fun getMiddleValue(list: List<Int>): Int {
            var middleCount = 0
            for (item in list) {
                middleCount += item
            }
            middleCount /= list.size
            return middleCount
        }
    }

}