package io.jawg.geojson

class LineString(
    coordinates: LineStringCoordinates,
    bbox: List<Double>? = null
) : Geometry<LineStringCoordinates>("LineString", coordinates, bbox) {

  init {
    LineStringValidator.validate(coordinates)
  }

}