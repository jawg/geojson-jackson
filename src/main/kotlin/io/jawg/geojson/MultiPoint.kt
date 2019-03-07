package io.jawg.geojson

class MultiPoint(
    coordinates: List<PointCoordinates>,
    bbox: BBox? = null
) : Geometry<List<PointCoordinates>>("MultiPoint", coordinates, bbox) {

  override fun getAllCoordinates(): List<Position> = coordinates.orEmpty()
}
