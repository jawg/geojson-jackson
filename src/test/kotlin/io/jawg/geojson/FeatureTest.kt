package io.jawg.geojson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.skyscreamer.jsonassert.JSONAssert
import kotlin.test.assertTrue

class FeatureTest {

  private val mapper = ObjectMapper().registerModule(KotlinModule())

  @Test
  fun `it should deserialize to Feature with id`() {
    val geojson = GeoJsonLoader.loadFeature("with-id.json")

    val feature = mapper.readValue(geojson, GeoJsonObject::class.java) as Feature

    assertEquals("p1", feature.id)
    assertTrue(feature.properties!!.isEmpty())
    assertTrue(feature.geometry is Point)
  }

  @Test
  fun `it should serialize to Feature with id`() {
    val feature = GeoJsonFactory.feature(id = true, properties = false)

    val geojson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(feature)

    val expected = GeoJsonLoader.loadFeature("with-id.json")

    JSONAssert.assertEquals(expected, geojson, true)
  }

  @Test
  fun `it should deserialize to Feature with properties`() {
    val geojson = GeoJsonLoader.loadFeature("with-properties.json")

    val feature = mapper.readValue(geojson, GeoJsonObject::class.java) as Feature

    val properties = GeoJsonFactory.feature(id = false, properties = true).properties

    assertNull(feature.id)
    assertEquals(properties, feature.properties)
    assertTrue(feature.geometry is Point)
  }

  @Test
  fun `it should serialize to Feature with properties`() {
    val feature = GeoJsonFactory.feature(id = false, properties = true)

    val geojson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(feature)

    val expected = GeoJsonLoader.loadFeature("with-properties.json")

    JSONAssert.assertEquals(expected, geojson, true)
  }
}