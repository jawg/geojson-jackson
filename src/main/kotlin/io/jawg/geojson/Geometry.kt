package io.jawg.geojson

abstract class Geometry<out T>(
    type: String,
    val coordinates: T?,
    val bbox: List<Double>?
) : GeoJsonObject(type)