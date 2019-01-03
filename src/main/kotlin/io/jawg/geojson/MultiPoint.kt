package io.jawg.geojson

class MultiPoint(
    coordinates: List<PointCoordinates>,
    bbox: List<Double>? = null
) : Geometry<List<PointCoordinates>>("MultiPoint", coordinates, bbox)