package io.jawg.geojson

class MultiPolygon(
    coordinates: List<PolygonCoordinates>,
    bbox: List<Double>? = null
) : Geometry<List<PolygonCoordinates>>("MultiPolygon", coordinates, bbox) {

  init {
    coordinates.forEach { PolygonValidator.validate(it) }
  }

}