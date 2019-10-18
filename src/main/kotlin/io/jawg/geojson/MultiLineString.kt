package io.jawg.geojson

data class MultiLineString(
    override val coordinates: List<LineStringCoordinates>,
    override val bbox: BBox? = null
) : Geometry<List<LineStringCoordinates>>("MultiLineString") {

  init {
    coordinates.forEach { LineStringValidator.validate(it) }
  }

  override fun getAllCoordinates(): List<Position> = coordinates.flatten()

}
