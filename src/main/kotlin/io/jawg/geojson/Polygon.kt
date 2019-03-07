package io.jawg.geojson

class Polygon(
    coordinates: PolygonCoordinates,
    bbox: BBox? = null
) : Geometry<PolygonCoordinates>("Polygon", coordinates, bbox) {

  init {
    PolygonValidator.validate(coordinates)
  }

  override fun getAllCoordinates(): List<Position> = coordinates?.flatten().orEmpty()
}
