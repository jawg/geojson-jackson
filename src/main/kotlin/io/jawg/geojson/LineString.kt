package io.jawg.geojson

data class LineString(
    override val coordinates: LineStringCoordinates,
    override val bbox: BBox? = null
) : Geometry<LineStringCoordinates>("LineString") {

  init {
    LineStringValidator.validate(coordinates)
  }

  override fun getAllCoordinates(): List<Position> = coordinates.orEmpty()

}
