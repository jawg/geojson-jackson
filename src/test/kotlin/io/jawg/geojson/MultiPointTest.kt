package io.jawg.geojson

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class MultiPointTest {

  private val mapper = jacksonObjectMapper()

  @Test
  fun `it should serialize to MultiPoint`() {
    val multiPoint = MultiPoint(
      coordinates = listOf(
        PointCoordinates(lng = 100.0, lat = 50.0),
        PointCoordinates(lng = 50.2, lat = 25.7)
      )
    )
    val geojson = mapper.writeValueAsString(multiPoint)

    val expected = """
      {
        "coordinates": [[100.0, 50.0], [50.2, 25.7]],
        "type": "MultiPoint"
      }
    """.replace("\\s".toRegex(), "")

    assertEquals(expected, geojson)
  }

  @Test
  fun `it should deserialize to MultiPoint`() {
    val geojson = """
      {
        "type": "MultiPoint",
        "coordinates": [[100.0, 50.0], [50.2, 25.7]]
      }
    """
    val multiPoint = mapper.readValue(geojson, GeoJsonObject::class.java)
    val expected = MultiPoint(
      coordinates = listOf(
        PointCoordinates(lng = 100.0, lat = 50.0),
        PointCoordinates(lng = 50.2, lat = 25.7)
      )
    )
    assertTrue(multiPoint is MultiPoint)
    assertEquals(expected.coordinates, multiPoint.coordinates)
  }

}