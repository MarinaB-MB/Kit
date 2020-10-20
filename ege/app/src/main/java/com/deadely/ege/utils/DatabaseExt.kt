package com.deadely.ege.utils

import com.deadely.ege.base.FIZ
import com.deadely.ege.base.MAT
import com.deadely.ege.base.RYS
import com.deadely.ege.database.*
import com.deadely.ege.model.Point
import com.deadely.ege.model.PointsObject

fun mapList(points: List<PointsObject>): PointEntity {
    points[0].fiz = points[0].fiz.sortedBy { it.second_point }
    points[0].ryss = points[0].ryss.sortedBy { it.second_point }
    points[0].infa = points[0].infa.sortedBy { it.second_point }
    points[0].math = points[0].math.sortedBy { it.second_point }
    return points[0].mapToEntity()
}

fun PointEntity.mapToEntity(): PointsObject {
    return PointsObject(
        math.map { Point(it.first_point, it.second_point) },
        infa.map { Point(it.first_point, it.second_point) },
        ryss.map { Point(it.first_point, it.second_point) },
        fiz.map { Point(it.first_point, it.second_point) }
    )
}

fun PointsObject.mapToEntity(): PointEntity {
    return PointEntity(
        0,
        math.map { it.mapToEntity() } as List<MathPointEntity>,
        infa.map { it.mapToEntity() } as List<InfaPointEntity>,
        ryss.map { it.mapToEntity() } as List<RyssPointEntity>,
        fiz.map { it.mapToEntity() } as List<FizPointEntity>
    )
}

fun Point.mapToEntity(): PointEntityOverClass {
    return when {
        first_point.contains(RYS) -> {
            RyssPointEntity(first_point, second_point)
        }
        first_point.contains(MAT) -> {
            MathPointEntity(first_point, second_point)
        }
        first_point.contains(FIZ) -> {
            FizPointEntity(first_point, second_point)
        }
        else -> {
            InfaPointEntity(first_point, second_point)
        }
    }
}
