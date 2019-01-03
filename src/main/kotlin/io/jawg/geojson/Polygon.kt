package io.jawg.geojson

class Polygon(
    coordinates: PolygonCoordinates,
    bbox: List<Double>? = null
) : Geometry<PolygonCoordinates>("Polygon", coordinates, bbox) {

  init {
    PolygonValidator.validate(coordinates)
  }

}