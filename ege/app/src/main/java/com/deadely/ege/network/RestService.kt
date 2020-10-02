package com.deadely.ege.network

import com.deadely.ege.base.GET_DISCIPLINES
import com.deadely.ege.base.GET_UNIVERSITIES
import com.deadely.ege.base.GET_VARIANT
import com.deadely.ege.model.Asks
import com.deadely.ege.model.Disciplines
import com.deadely.ege.model.University
import retrofit2.http.GET

interface RestService {

    @GET(GET_UNIVERSITIES)
    suspend fun getUniversities(): List<University>

    @GET(GET_DISCIPLINES)
    suspend fun getDisciplines(): List<Disciplines>

    @GET(GET_VARIANT)
    suspend fun getAsks(): List<Asks>

}