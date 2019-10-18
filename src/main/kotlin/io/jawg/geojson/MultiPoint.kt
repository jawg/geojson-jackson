package io.jawg.geojson

data class MultiPoint(
    override val coordinates: List<PointCoordinates>,
    override val bbox: BBox? = null
) : Geometry<List<PointCoordinates>>("MultiPoint") {

  override fun getAllCoordinates(): List<Position> = coordinates

}
