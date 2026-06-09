package io.jawg.geojson

import io.jawg.geojson.serializer.PositionDeserializer
import io.jawg.geojson.serializer.PositionSerializer
import tools.jackson.databind.annotation.JsonDeserialize
import tools.jackson.databind.annotation.JsonSerialize

typealias PointCoordinates = Position
typealias LineStringCoordinates = List<Position>
typealias LinearRing = List<Position>
typealias PolygonCoordinates = List<LinearRing>

@JsonDeserialize(using = PositionDeserializer::class)
@JsonSerialize(using = PositionSerializer::class)
data class Position(val lng: Double, val lat: Double, val alt: Double? = null)
