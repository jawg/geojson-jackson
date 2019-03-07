package io.jawg.geojson

class GeometryCollection(
    val geometries: List<Geometry<*>>,
    bbox: BBox? = null
) : Geometry<Nothing>("GeometryCollection", null, bbox) {
  override fun getAllCoordinates(): List<Position> = geometries.flatMap { it.getAllCoordinates() }
}
