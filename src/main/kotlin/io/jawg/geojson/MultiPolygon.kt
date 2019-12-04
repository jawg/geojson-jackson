package io.jawg.geojson

data class MultiPolygon(
    override val coordinates: List<PolygonCoordinates>,
    override val bbox: BBox? = null
) : Geometry<List<PolygonCoordinates>>("MultiPolygon") {

  init {
    coordinates.forEach { PolygonValidator.validate(it) }
  }

  override fun getAllCoordinates(): List<Position> = coordinates.flatMap { it.flatten() }

}
