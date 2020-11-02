package com.deadely.ege.network

import com.deadely.ege.model.Asks
import com.deadely.ege.model.Disciplines
import com.deadely.ege.model.PointsObject
import com.deadely.ege.model.University
import com.deadely.ege.utils.GET_ASKS
import com.deadely.ege.utils.GET_DISCIPLINES
import com.deadely.ege.utils.GET_POINTS
import com.deadely.ege.utils.GET_UNIVERSITIES
import retrofit2.http.GET

interface RestService {

    @GET(GET_UNIVERSITIES)
    suspend fun getUniversities(): List<University>

    @GET(GET_DISCIPLINES)
    suspend fun getDisciplines(): List<Disciplines>

    @GET(GET_ASKS)
    suspend fun getAsks(): List<Asks>

    @GET(GET_POINTS)
    suspend fun getPoints(): List<PointsObject>

}