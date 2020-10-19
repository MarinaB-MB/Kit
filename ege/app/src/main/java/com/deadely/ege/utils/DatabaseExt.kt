package com.deadely.ege.utils

import com.deadely.ege.base.FIZ
import com.deadely.ege.base.MAT
import com.deadely.ege.base.RYS
import com.deadely.ege.database.*
import com.deadely.ege.model.Point
import com.deadely.ege.model.PointsObject

fun MathPointEntity.mapToPoint(): Point {
    return Point(first_point, second_point)
}

fun List<MathPointEntity>.mapToMathPointList(): List<Point> {
    return map { it.mapToPoint() }
}

fun InfaPointEntity.mapToPoint(): Point {
    return Point(first_point, second_point)
}

fun List<InfaPointEntity>.mapToInfaPointList(): List<Point> {
    return map { it.mapToPoint() }
}

fun RyssPointEntity.mapToPoint(): Point {
    return Point(first_point, second_point)
}

fun List<RyssPointEntity>.mapToRyssPointList(): List<Point> {
    return map { it.mapToPoint() }
}

fun FizPointEntity.mapToPoint(): Point {
    return Point(first_point, second_point)
}

fun List<FizPointEntity>.mapToIFizPointList(): List<Point> {
    return map { it.mapToPoint() }
}

fun PointEntity.mapToEnity(): PointsObject {
    return PointsObject(
        math.mapToMathPointList(),
        infa.mapToInfaPointList(),
        ryss.mapToRyssPointList(),
        fiz.mapToIFizPointList()
    )
}

fun List<PointEntity>.mapToPointList(): List<PointsObject> {
    return map { it.mapToEnity() }
}

fun PointsObject.mapToEntity(): PointEntity {
    return PointEntity(
        0,
        math.mapToEntity() as List<MathPointEntity>,
        infa.mapToEntity() as List<InfaPointEntity>,
        ryss.mapToEntity() as List<RyssPointEntity>,
        fiz.mapToEntity() as List<FizPointEntity>
    )
}

fun List<Point>.mapToEntity(): List<PointEntityOverClass> {
    return map { it.mapToEntity() }
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
