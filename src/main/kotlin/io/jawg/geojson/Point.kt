package io.jawg.geojson

class Point(
    coordinates: PointCoordinates,
    bbox: BBox? = null
) : Geometry<Position>("Point", coordinates, bbox) {

  override fun getAllCoordinates(): List<Position> = coordinates?.let { listOf(it) } ?: emptyList()
}
