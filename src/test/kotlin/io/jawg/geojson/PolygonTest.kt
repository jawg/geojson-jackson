package io.jawg.geojson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.jawg.geojson.utils.GeoJsonLoader
import org.junit.Assert.assertEquals
import org.junit.Test
import org.skyscreamer.jsonassert.JSONAssert
import kotlin.test.assertFailsWith

class PolygonTest {

  private val mapper = ObjectMapper().registerModule(KotlinModule())

  @Test
  fun `it should deserialize to Polygon no holes`() {
    val geojson = GeoJsonLoader.loadPolygon("no-holes.json")

    val polygon: Polygon = mapper.readValue(geojson, GeoJsonObject::class.java) as Polygon
    assertEquals(1, polygon.coordinates.size)
    assertEquals(5, polygon.coordinates.first().size)
  }

  @Test
  fun `it should deserialize to Polygon with holes`() {
    val geojson = GeoJsonLoader.loadPolygon("with-holes.json")

    val polygon: Polygon = mapper.readValue(geojson, GeoJsonObject::class.java) as Polygon
    assertEquals(2, polygon.coordinates.size)
    assertEquals(5, polygon.coordinates.first().size)
    assertEquals(5, polygon.coordinates.last().size)
  }

  @Test
  fun `it should serialize to Polygon no holes`() {
    val ring = listOf(
        Position(100.0, 0.0),
        Position(101.0, 0.0),
        Position(101.0, 1.0),
        Position(100.0, 1.0),
        Position(100.0, 0.0)
    )
    val polygon = Polygon(listOf(ring))

    val geojson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(polygon)

    val expected = GeoJsonLoader.loadPolygon("no-holes.json")

    JSONAssert.assertEquals(expected, geojson, false)
  }

  @Test
  fun `it should serialize to Polygon with holes`() {
    val ring = listOf(
        Position(100.0, 0.0),
        Position(101.0, 0.0),
        Position(101.0, 1.0),
        Position(100.0, 1.0),
        Position(100.0, 0.0)
    )
    val hole = listOf(
        Position(100.8, 0.8),
        Position(100.8, 0.2),
        Position(100.2, 0.2),
        Position(100.2, 0.8),
        Position(100.8, 0.8)
    )
    val polygon = Polygon(listOf(ring, hole))

    val geojson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(polygon)

    val expected = GeoJsonLoader.loadPolygon("with-holes.json")

    JSONAssert.assertEquals(expected, geojson, false)
  }

  @Test
  fun `it should fail when linear ring is not closed`() {
    val unclosedRing = listOf(
        Position(100.0, 0.0),
        Position(101.0, 0.0),
        Position(101.0, 1.0),
        Position(100.0, 1.0)
    )

    assertFailsWith(IllegalArgumentException::class) {
      Polygon(listOf(unclosedRing))
    }

  }

  @Test
  fun `it should fail when linear ring size is less than 4`() {
    val ring = listOf(
        Position(100.0, 0.0),
        Position(101.0, 0.0),
        Position(100.0, 0.0)
    )

    assertFailsWith(IllegalArgumentException::class) {
      Polygon(listOf(ring))
    }
  }
}
