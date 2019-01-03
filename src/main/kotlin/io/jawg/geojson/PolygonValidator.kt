package io.jawg.geojson

internal object PolygonValidator {

  fun validate(coordinates: PolygonCoordinates) {
    require(coordinates.isNotEmpty()) { "Polygon coordinates must have at least one linear ring" }

    coordinates.forEach { linearRing ->
      require(linearRing.size >= 4) { "Polygon linear ring must have at least 4 coordinates" }
      require(linearRing.first() == linearRing.last()) {
        "Polygon linear ring first element must be equal to last element"
      }
    }
  }

}