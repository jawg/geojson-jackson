package io.jawg.geojson.utils

internal object GeoJsonLoader {

  private const val BASE_PATH = "/geojson"

  fun loadPolygon(name: String): String {
    val path = BASE_PATH + "/polygon/$name"
    return load(path)
  }

  fun loadMultiLineString(name: String): String {
    val path = BASE_PATH + "/multilinestring/$name"
    return load(path)
  }

  fun loadMultiPolygon(name: String): String {
    val path = BASE_PATH + "/multipolygon/$name"
    return load(path)
  }

  fun loadGeometryCollection(name: String): String {
    val path = BASE_PATH + "/geometrycollection/$name"
    return load(path)
  }

  fun loadFeature(name: String): String {
    val path = BASE_PATH + "/feature/$name"
    return load(path)
  }

  private fun load(path: String): String {
    return GeoJsonLoader::class.java.getResource(path).readText()
  }

}
