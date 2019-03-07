package io.jawg.geojson

class MultiLineString(
    coordinates: List<LineStringCoordinates>,
    bbox: BBox? = null
) : Geometry<List<LineStringCoordinates>>("MultiLineString", coordinates, bbox) {

  init {
    coordinates.forEach { LineStringValidator.validate(it) }
  }

  override fun getAllCoordinates(): List<Position> = coordinates?.flatten().orEmpty()
}
