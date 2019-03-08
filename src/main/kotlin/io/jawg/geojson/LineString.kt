package io.jawg.geojson

class LineString(
    coordinates: LineStringCoordinates,
    bbox: BBox? = null
) : Geometry<LineStringCoordinates>("LineString", coordinates, bbox) {

  init {
    LineStringValidator.validate(coordinates)
  }

  override fun getAllCoordinates(): List<Position> = coordinates.orEmpty()

}
