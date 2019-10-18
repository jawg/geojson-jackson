package io.jawg.geojson

data class GeometryCollection(
    val geometries: List<Geometry<*>>,
    override val bbox: BBox? = null
) : Geometry<Nothing>("GeometryCollection") {

  override val coordinates = null

  override fun getAllCoordinates(): List<Position> = geometries.flatMap { it.getAllCoordinates() }

}
