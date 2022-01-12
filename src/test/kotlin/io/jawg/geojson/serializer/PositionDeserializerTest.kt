package io.jawg.geojson.serializer

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.jawg.geojson.Position
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PositionDeserializerTest {

  private val mapper = jacksonObjectMapper()

  @Test
  fun `it should deserialize double (lng, lat, alt)`() {
    val geojson = """
      [1.1, 2.2, 3.3]
    """
    val expected = Position(
      lng = 1.1,
      lat = 2.2,
      alt = 3.3
    )
    val position = mapper.readValue(geojson, Position::class.java)

    assertEquals(expected, position)
  }

  @Test
  fun `it should deserialize integer (lng, lat, alt)`() {
    val geojson = """
      [1, 2, 3]
    """
    val expected = Position(
      lng = 1.0,
      lat = 2.0,
      alt = 3.0
    )
    val position = mapper.readValue(geojson, Position::class.java)

    assertEquals(expected, position)
  }

  @Test
  fun `it should deserialize string (lng, lat, alt)`() {
    val geojson = """
      ["1.0", "2.0", "3.0"]
    """
    val expected = Position(
      lng = 1.0,
      lat = 2.0,
      alt = 3.0
    )
    val position = mapper.readValue(geojson, Position::class.java)

    assertEquals(expected, position)
  }

  @Test
  fun `it should deserialize (lng, lat)`() {
    val geojson = """
      ["1.0", "2.0"]
    """
    val expected = Position(
      lng = 1.0,
      lat = 2.0,
      alt = null
    )
    val position = mapper.readValue(geojson, Position::class.java)

    assertEquals(expected, position)
  }

  @Test
  fun `it should fail to deserialize (lng)`() {
    val geojson = """
      ["1.0"]
    """
    assertFailsWith(MismatchedInputException::class) {
      mapper.readValue(geojson, Position::class.java)
    }
  }

  @Test
  fun `it should fail to deserialize empty ()`() {
    val geojson = """
      []
    """
    assertFailsWith(MismatchedInputException::class) {
      mapper.readValue(geojson, Position::class.java)
    }
  }

  @Test
  fun `it should ignore more than 3 elements`() {
    val geojson = """
      ["1.0", "2.0", "3.0", "4.0"]
    """
    val expected = Position(
      lng = 1.0,
      lat = 2.0,
      alt = 3.0
    )
    val position = mapper.readValue(geojson, Position::class.java)

    assertEquals(expected, position)
  }
}