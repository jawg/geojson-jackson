package io.jawg.geojson

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.Test
import org.skyscreamer.jsonassert.JSONAssert
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class LineStringTest {

  private val mapper = jacksonObjectMapper()

  @Test
  fun `it should serialize to LineString`() {
    val lineString = LineString(
      coordinates = listOf(
        Position(lng = 100.0, lat = 50.0),
        Position(lng = 50.2, lat = 15.6)
      )
    )
    val geojson = mapper.writeValueAsString(lineString)

    val expected = """
      {
        "coordinates": [[100.0, 50.0], [50.2, 15.6]],
        "type": "LineString"
      }
    """.replace("\\s".toRegex(), "")

    assertEquals(expected, geojson)
  }

  @Test
  fun `it should deserialize to LineString`() {
    val geojson = """
      {
        "type": "LineString",
        "coordinates": [[100.0, 50.0], [50.2, 15.6]]
      }
    """
    val lineString = mapper.readValue(geojson, GeoJsonObject::class.java)
    val expected = LineString(
      coordinates = listOf(
        Position(lng = 100.0, lat = 50.0),
        Position(lng = 50.2, lat = 15.6)
      )
    )
    assertTrue(lineString is LineString)
    assertEquals(expected.coordinates, lineString.coordinates)
  }

  @Test
  fun `it should serialize to LineString with bbox`() {
    val lineString = LineString(
      coordinates = listOf(
        Position(lng = 100.0, lat = 50.0),
        Position(lng = 50.2, lat = 15.6)
      ),
      bbox = BBox(50.2, 15.6, 100.0, 50.0)
    )
    val geojson = mapper.writeValueAsString(lineString)

    val expected = """
      {
        "coordinates": [[100.0, 50.0], [50.2, 15.6]],
        "bbox": [50.2, 15.6, 100.0, 50.0],
        "type": "LineString"
      }
    """

    JSONAssert.assertEquals(expected, geojson, true)
  }

  @Test
  fun `it should deserialize to LineString with bbox`() {
    val geojson = """
      {
        "type": "LineString",
        "coordinates": [[100.0, 50.0], [50.2, 15.6]],
        "bbox": [50.2, 15.6, 100.0, 50.0]
      }
    """
    val lineString = mapper.readValue(geojson, GeoJsonObject::class.java)
    val expected = LineString(
      coordinates = listOf(
        Position(lng = 100.0, lat = 50.0),
        Position(lng = 50.2, lat = 15.6)
      ),
      bbox = BBox(50.2, 15.6, 100.0, 50.0)
    )
    assertTrue(lineString is LineString)
    assertEquals(expected.coordinates, lineString.coordinates)
    assertEquals(expected.bbox, lineString.bbox)
  }
}
