package io.jawg.geojson

import io.jawg.geojson.utils.GeoJsonFactory
import io.jawg.geojson.utils.GeoJsonLoader
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.skyscreamer.jsonassert.JSONAssert
import tools.jackson.module.kotlin.jacksonObjectMapper

class GeometryCollectionTest {

  private val mapper = jacksonObjectMapper()

  @Test
  fun `it should deserialize to GeometryCollection`() {
    val geojson = GeoJsonLoader.loadGeometryCollection("2-geometries.json")

    val geometryCollection = mapper.readValue(geojson, GeoJsonObject::class.java) as GeometryCollection

    assertNull(geometryCollection.coordinates)
    assertEquals(2, geometryCollection.geometries.size)
    assertTrue(geometryCollection.geometries[0] is Point)
    assertTrue(geometryCollection.geometries[1] is LineString)
  }

  @Test
  fun `it should serialize to GeometryCollection`() {
    val geometryCollection = GeoJsonFactory.geometryCollection()

    val geojson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(geometryCollection)

    val expected = GeoJsonLoader.loadGeometryCollection("2-geometries.json")

    JSONAssert.assertEquals(expected, geojson, false)
  }
}
