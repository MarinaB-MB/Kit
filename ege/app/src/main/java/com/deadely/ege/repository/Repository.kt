package com.deadely.ege.repository

import com.deadely.ege.model.Asks
import com.deadely.ege.model.Disciplines
import com.deadely.ege.model.Point
import com.deadely.ege.model.University
import com.deadely.ege.network.RestService
import com.deadely.ege.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class Repository
@Inject constructor(private val api: RestService) {

    suspend fun getUniversitiesList(): Flow<DataState<List<University>>> = flow {
        try {
            emit(DataState.Loading)
            val universities = api.getUniversities()
            emit(DataState.Success(universities))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getDisciplinesList(): Flow<DataState<List<Disciplines>>> = flow {
        try {
            emit(DataState.Loading)
            val disciplines = api.getDisciplines()
            emit(DataState.Success(disciplines))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getAsks(): Flow<DataState<List<Asks>>> = flow {
        try {
            emit(DataState.Loading)
            val asks = api.getAsks()
            emit(DataState.Success(asks))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }


    suspend fun getMiddle(list: List<String>): Flow<DataState<List<Point>>> = flow {
        try {
            emit(DataState.Loading)
            val point = mutableListOf<Point>()
            for (item in list) {
                point.addAll(api.getSecondPoint("{\"first_point\":\"$item\"}"))
            }
            emit(DataState.Success(point))
        } catch (e: java.lang.Exception) {
            emit(DataState.Error(e))
        }
    }
}