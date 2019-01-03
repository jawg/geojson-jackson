package io.jawg.geojson

class MultiLineString(
    coordinates: List<LineStringCoordinates>,
    bbox: List<Double>? = null
) : Geometry<List<LineStringCoordinates>>("MultiLineString", coordinates, bbox) {

  init {
    coordinates.forEach { LineStringValidator.validate(it) }
  }
}