package io.jawg.geojson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.jawg.geojson.utils.GeoJsonFactory
import io.jawg.geojson.utils.GeoJsonLoader
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.skyscreamer.jsonassert.JSONAssert
import kotlin.test.assertTrue

class FeatureTest {

  private val mapper = ObjectMapper().registerKotlinModule()

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
  fun `it should deserialize to Feature with 3 dimensions`() {
    val geojson = GeoJsonLoader.loadFeature("with-3-dimensions.json")

    val feature = mapper.readValue<Feature>(geojson)

    val expected = GeoJsonFactory.feature(id = false, alt = true, properties = true)

    assertEquals(expected, feature)
  }

  @Test
  fun `it should serialize to Feature with 3 dimensions`() {
    val feature = GeoJsonFactory.feature(id = false, alt = true, properties = true)

    val geojson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(feature)

    val expected = GeoJsonLoader.loadFeature("with-3-dimensions.json")

    JSONAssert.assertEquals(expected, geojson, true)
  }

  @Test
  fun `it should deserialize to Feature with properties`() {
    val geojson = GeoJsonLoader.loadFeature("with-properties.json")

    val feature = mapper.readValue<Feature>(geojson)

    val expected = GeoJsonFactory.feature(id = false, properties = true)

    assertEquals(expected, feature)
  }

  @Test
  fun `it should serialize to Feature with properties`() {
    val feature = GeoJsonFactory.feature(id = false, properties = true)

    val geojson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(feature)

    val expected = GeoJsonLoader.loadFeature("with-properties.json")

    JSONAssert.assertEquals(expected, geojson, true)
  }

  @Test
  fun `it should deserialize to Feature with bbox`() {
    val geojson = GeoJsonLoader.loadFeature("with-bbox.json")

    val feature = mapper.readValue(geojson, GeoJsonObject::class.java) as Feature

    val bbox = GeoJsonFactory.feature(id = false, properties = false, bbox = true).bbox

    assertNull(feature.id)
    assertEquals(bbox, feature.bbox)
    assertTrue(feature.geometry is Point)
  }

  @Test
  fun `it should serialize to feature with bbox`() {
    val feature = GeoJsonFactory.feature(id = false, properties = false, bbox = true)

    val geojson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(feature)

    val expected = GeoJsonLoader.loadFeature("with-bbox.json")

    JSONAssert.assertEquals(expected, geojson, true)
  }
}
