package com.deadely.ege.utils

import com.deadely.ege.database.*
import com.deadely.ege.model.Point
import com.deadely.ege.model.PointsObject

fun mapList(points: List<PointsObject>): PointEntity {
    points[0].fiz = points[0].fiz.sortedBy { it.secondPoint }
    points[0].ryss = points[0].ryss.sortedBy { it.secondPoint }
    points[0].infa = points[0].infa.sortedBy { it.secondPoint }
    points[0].math = points[0].math.sortedBy { it.secondPoint }
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
        firstPoint.contains(RYS) -> {
            RyssPointEntity(firstPoint, secondPoint)
        }
        firstPoint.contains(MAT) -> {
            MathPointEntity(firstPoint, secondPoint)
        }
        firstPoint.contains(FIZ) -> {
            FizPointEntity(firstPoint, secondPoint)
        }
        else -> {
            InfaPointEntity(firstPoint, secondPoint)
        }
    }
}
