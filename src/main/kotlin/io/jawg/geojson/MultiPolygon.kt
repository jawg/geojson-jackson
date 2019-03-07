package io.jawg.geojson

class MultiPolygon(
    coordinates: List<PolygonCoordinates>,
    bbox: BBox? = null
) : Geometry<List<PolygonCoordinates>>("MultiPolygon", coordinates, bbox) {

  init {
    coordinates.forEach { PolygonValidator.validate(it) }
  }

  override fun getAllCoordinates(): List<Position> = coordinates?.flatMap { it.flatten() }.orEmpty()
}
