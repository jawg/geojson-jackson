package io.jawg.geojson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.jawg.geojson.dsl.toBBox
import org.junit.Test
import org.skyscreamer.jsonassert.JSONAssert
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PointTest {

  private val mapper = ObjectMapper().registerModule(KotlinModule())

  @Test
  fun `it should serialize to Point(lng, lat)`() {
    val point = Point(coordinates = PointCoordinates(lng = 100.0, lat = 50.0))
    val geojson = mapper.writeValueAsString(point)

    val expected = """
      {
        "coordinates": [100.0, 50.0],
        "type": "Point"
      }
    """.replace("\\s".toRegex(), "")

    assertEquals(expected, geojson)
  }

  @Test
  fun `it should deserialize to Point(lng, lat)`() {
    val geojson = """
      {
        "type": "Point",
        "coordinates": [100.0, 50.0]
      }
    """
    val point = mapper.readValue(geojson, GeoJsonObject::class.java)
    val expected = Point(coordinates = PointCoordinates(lng = 100.0, lat = 50.0))
    assertTrue(point is Point)
    assertEquals(expected.coordinates, point.coordinates)
  }

  @Test
  fun `it should serialize to Point(lng, lat, alt)`() {
    val point = Point(coordinates = PointCoordinates(lng = 100.0, lat = 50.0, alt = 25.3))
    val geojson = mapper.writeValueAsString(point)

    val expected = """
      {
        "coordinates": [100.0, 50.0, 25.3],
        "type": "Point"
      }
    """.replace("\\s".toRegex(), "")

    assertEquals(expected, geojson)
  }

  @Test
  fun `it should deserialize to Point(lng, lat, alt)`() {
    val geojson = """
      {
        "type": "Point",
        "coordinates": [100.1, 50.2, 25.3]
      }
    """
    val point = mapper.readValue(geojson, GeoJsonObject::class.java)
    val expected = Point(coordinates = PointCoordinates(lng = 100.1, lat = 50.2, alt = 25.3))
    assertTrue(point is Point)
    assertEquals(expected.coordinates, point.coordinates)
  }

  @Test
  fun `it should serialize to Point(lng, lat) with bbox`() {
    val coordinate = PointCoordinates(lng = 100.0, lat = 50.0)
    val point = Point(
      coordinates = coordinate,
      bbox = listOf(coordinate).toBBox()
    )
    val geojson = mapper.writeValueAsString(point)

    val expected = """
      {
        "coordinates": [100.0, 50.0],
        "bbox": [100.0, 50.0, 100.0, 50.0],
        "type": "Point"
      }
    """

    JSONAssert.assertEquals(expected, geojson, true)
  }

  @Test
  fun `it should deserialize to Point(lng, lat) with bbox`() {
    val geojson = """
      {
        "type": "Point",
        "coordinates": [100.0, 50.0],
        "bbox": [100.0, 50.0, 100.0, 50.0]
      }
    """
    val point = mapper.readValue(geojson, GeoJsonObject::class.java)
    val expectedCoordinate = PointCoordinates(lng = 100.0, lat = 50.0)
    val expectedPoint = Point(
      coordinates = expectedCoordinate,
      bbox = listOf(expectedCoordinate).toBBox()
    )

    assertTrue(point is Point)
    assertEquals(expectedPoint.bbox, point.bbox)
  }
}
