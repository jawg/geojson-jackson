package io.jawg.geojson

import com.fasterxml.jackson.annotation.JsonIgnore

abstract class Geometry<out T>(type: String) : GeoJsonObject(type) {

  abstract val coordinates: T?
  abstract val bbox: BBox?

  @JsonIgnore
  internal abstract fun getAllCoordinates(): List<Position>
}
