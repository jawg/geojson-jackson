package io.jawg.geojson

data class Polygon(
    override val coordinates: PolygonCoordinates,
    override val bbox: BBox? = null
) : Geometry<PolygonCoordinates>("Polygon") {

  init {
    PolygonValidator.validate(coordinates)
  }

  override fun getAllCoordinates(): List<Position> = coordinates.flatten()

}
