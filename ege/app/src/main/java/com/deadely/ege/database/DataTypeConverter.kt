package com.deadely.ege.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.util.*


class MathPointEntityTypeConverter : Serializable {
    val gson = Gson()

    @TypeConverter
    fun stringToMathPointEntityList(data: String?): List<MathPointEntity> {
        data?.let {
            val listType = object : TypeToken<List<MathPointEntity>>() {}.type
            return gson.fromJson<List<MathPointEntity>>(data, listType)
        } ?: let { return Collections.emptyList() }

    }

    @TypeConverter
    fun mathPointEntityListToString(list: List<MathPointEntity>): String {
        return gson.toJson(list)
    }
}

class InfaPointEntityTypeConverter : Serializable {
    val gson = Gson()

    @TypeConverter
    fun stringToInfaPointEntityList(data: String?): List<InfaPointEntity> {
        data?.let {
            val listType = object : TypeToken<List<InfaPointEntity>>() {}.type
            return gson.fromJson<List<InfaPointEntity>>(data, listType)
        } ?: let { return Collections.emptyList() }

    }

    @TypeConverter
    fun infaPointEntityListToString(list: List<InfaPointEntity>): String {
        return gson.toJson(list)
    }
}

class FizikaPointEntityTypeConverter : Serializable {
    val gson = Gson()

    @TypeConverter
    fun stringToFizPointEntityList(data: String?): List<FizPointEntity> {
        data?.let {
            val listType = object : TypeToken<List<FizPointEntity>>() {}.type
            return gson.fromJson<List<FizPointEntity>>(data, listType)
        } ?: let { return Collections.emptyList() }

    }

    @TypeConverter
    fun fizPointEntityListToString(list: List<FizPointEntity>): String {
        return gson.toJson(list)
    }
}

class RysskiuPointEntityTypeConverter : Serializable {
    val gson = Gson()

    @TypeConverter
    fun stringToRyssPointEntityList(data: String?): List<RyssPointEntity> {
        data?.let {
            val listType = object : TypeToken<List<RyssPointEntity>>() {}.type
            return gson.fromJson<List<RyssPointEntity>>(data, listType)
        } ?: let { return Collections.emptyList() }

    }

    @TypeConverter
    fun ryssPointEntityListToString(list: List<RyssPointEntity>): String {
        return gson.toJson(list)
    }
}