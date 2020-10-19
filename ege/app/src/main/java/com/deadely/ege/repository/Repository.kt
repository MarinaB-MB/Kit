package com.deadely.ege.repository

import com.deadely.ege.database.AppDataBase
import com.deadely.ege.model.Asks
import com.deadely.ege.model.Disciplines
import com.deadely.ege.model.PointsObject
import com.deadely.ege.model.University
import com.deadely.ege.network.RestService
import com.deadely.ege.utils.DataState
import com.deadely.ege.utils.Utils
import com.deadely.ege.utils.mapToPointList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class Repository
@Inject constructor(private val api: RestService, private val db: AppDataBase) {

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

    suspend fun getPoints(): Flow<DataState<PointsObject>> = flow {
        try {
            emit(DataState.Loading)
            val points = if (db.pointsDao().getPoints().isNullOrEmpty()) {
                api.getPoints()
            } else {
                db.pointsDao().getPoints().mapToPointList()
            }
            db.pointsDao().addAllToPoints(Utils.mapList(points))
            emit(DataState.Success(points[0]))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}