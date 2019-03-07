package io.jawg.geojson.utils

import io.jawg.geojson.BBox
import io.jawg.geojson.Position
import kotlin.math.max
import kotlin.math.min

object BBoxFactory {
  fun fromCoordinates(positions: List<Position>): BBox {
    var minLat = Double.POSITIVE_INFINITY
    var maxLat = Double.NEGATIVE_INFINITY
    var minLng = Double.POSITIVE_INFINITY
    var maxLng = Double.NEGATIVE_INFINITY
    positions.forEach { position ->
      minLat = min(minLat, position.lat)
      maxLat = max(maxLat, position.lat)
      minLng = min(minLng, position.lng)
      maxLng = max(maxLng, position.lng)
    }

    return BBox(
      west = minLng,
      east = maxLng,
      north = maxLat,
      south = minLat
    )
  }
}
