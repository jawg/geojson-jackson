package io.jawg.geojson

class Point(
    coordinates: PointCoordinates,
    bbox: List<Double>? = null
) : Geometry<Position>("Point", coordinates, bbox)