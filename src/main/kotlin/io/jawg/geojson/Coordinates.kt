package io.jawg.geojson

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.jawg.geojson.serializer.PositionDeserializer
import io.jawg.geojson.serializer.PositionSerializer

typealias PointCoordinates = Position
typealias LineStringCoordinates = List<Position>
typealias LinearRing = List<Position>
typealias PolygonCoordinates = List<LinearRing>

@JsonDeserialize(using = PositionDeserializer::class)
@JsonSerialize(using = PositionSerializer::class)
data class Position(val lng: Double, val lat: Double, val alt: Double? = null)