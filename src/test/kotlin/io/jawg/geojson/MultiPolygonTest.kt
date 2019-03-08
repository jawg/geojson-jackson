package io.jawg.geojson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.jawg.geojson.utils.GeoJsonLoader
import org.junit.Test
import org.skyscreamer.jsonassert.JSONAssert
import kotlin.test.assertFailsWith

class MultiPolygonTest {

  private val mapper = ObjectMapper().registerModule(KotlinModule())

  @Test
  fun `it should deserialize to MultiPolygon`() {
    val geojson = GeoJsonLoader.loadMultiPolygon("2-polygons.json")

    val multiPolygon = mapper.readValue(geojson, GeoJsonObject::class.java) as MultiPolygon

    kotlin.test.assertEquals(2, multiPolygon.coordinates!!.size)
  }

  @Test
  fun `it should serialize to MultiPolygon`() {
    val p1r1 = listOf(
        Position(102.0, 2.0),
        Position(103.0, 2.0),
        Position(103.0, 3.0),
        Position(102.0, 3.0),
        Position(102.0, 2.0)
    )

    val p2r1 = listOf(
        Position(100.0, 0.0),
        Position(101.0, 0.0),
        Position(101.0, 1.0),
        Position(100.0, 1.0),
        Position(100.0, 0.0)
    )

    val p2r2 = listOf(
        Position(100.2, 0.2),
        Position(100.2, 0.8),
        Position(100.8, 0.8),
        Position(100.8, 0.2),
        Position(100.2, 0.2)
    )

    val p1 = listOf(p1r1)
    val p2 = listOf(p2r1, p2r2)

    val multiPolygon = MultiPolygon(listOf(p1, p2))

    val geojson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(multiPolygon)

    val expected = GeoJsonLoader.loadMultiPolygon("2-polygons.json")

    JSONAssert.assertEquals(expected, geojson, false)
  }

  @Test
  fun `it should fail when one ring is not closed`() {
    val p1r1 = listOf(
        Position(102.0, 2.0),
        Position(103.0, 2.0),
        Position(103.0, 3.0),
        Position(102.0, 3.0),
        Position(102.0, 2.0)
    )

    val p2r1 = listOf(
        Position(100.0, 0.0),
        Position(101.0, 0.0),
        Position(101.0, 1.0),
        Position(100.0, 1.0),
        Position(100.0, 0.0)
    )

    val p2r2 = listOf(
        Position(100.2, 0.2),
        Position(100.2, 0.8),
        Position(100.8, 0.8),
        Position(100.8, 0.2),
        Position(100.3, 0.3)
    )

    val p1 = listOf(p1r1)
    val p2 = listOf(p2r1, p2r2)

    assertFailsWith(IllegalArgumentException::class) {
      MultiPolygon(listOf(p1, p2))
    }
  }

  @Test
  fun `it should fail when one linear ring size is less than 4`() {
    val p1r1 = listOf(
        Position(102.0, 2.0),
        Position(103.0, 2.0),
        Position(103.0, 3.0),
        Position(102.0, 3.0),
        Position(102.0, 2.0)
    )

    val p2r1 = listOf(
        Position(100.0, 0.0),
        Position(101.0, 0.0),
        Position(101.0, 1.0),
        Position(100.0, 1.0),
        Position(100.0, 0.0)
    )

    val p2r2 = listOf(
        Position(100.2, 0.2),
        Position(100.2, 0.8),
        Position(100.2, 0.2)
    )

    val p1 = listOf(p1r1)
    val p2 = listOf(p2r1, p2r2)

    assertFailsWith(IllegalArgumentException::class) {
      MultiPolygon(listOf(p1, p2))
    }
  }
}
