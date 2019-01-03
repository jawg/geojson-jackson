package io.jawg.geojson

class GeometryCollection(
    val geometries: List<Geometry<*>>,
    bbox: List<Double>? = null
) : Geometry<Nothing>("GeometryCollection", null, bbox)