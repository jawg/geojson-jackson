package io.jawg.geojson

data class Point(
    override val coordinates: PointCoordinates,
    override val bbox: BBox? = null
) : Geometry<Position>("Point") {

  override fun getAllCoordinates(): List<Position> = listOf(coordinates)

}
