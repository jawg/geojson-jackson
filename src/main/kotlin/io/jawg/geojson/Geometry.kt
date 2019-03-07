package io.jawg.geojson

import com.fasterxml.jackson.annotation.JsonIgnore

abstract class Geometry<out T>(
    type: String,
    val coordinates: T?,
    val bbox: BBox?
) : GeoJsonObject(type) {

  @JsonIgnore
  internal abstract fun getAllCoordinates(): List<Position>
}
