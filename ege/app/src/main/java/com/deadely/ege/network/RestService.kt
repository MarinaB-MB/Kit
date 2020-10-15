package com.deadely.ege.network

import com.deadely.ege.base.GET_ASKS
import com.deadely.ege.base.GET_DISCIPLINES
import com.deadely.ege.base.GET_SECOND_POINT
import com.deadely.ege.base.GET_UNIVERSITIES
import com.deadely.ege.model.Asks
import com.deadely.ege.model.Disciplines
import com.deadely.ege.model.Point
import com.deadely.ege.model.University
import retrofit2.http.GET
import retrofit2.http.Query

interface RestService {

    @GET(GET_UNIVERSITIES)
    suspend fun getUniversities(): List<University>

    @GET(GET_DISCIPLINES)
    suspend fun getDisciplines(): List<Disciplines>

    @GET(GET_ASKS)
    suspend fun getAsks(): List<Asks>

    @GET(GET_SECOND_POINT)
    suspend fun getSecondPoint(@Query("q") firstPoint: String): List<Point>
}