package io.jawg.geojson.serializer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.jawg.geojson.Position
import org.junit.Test
import kotlin.test.assertEquals

class PositionSerializerTest {

  private val mapper = ObjectMapper().registerKotlinModule()

  @Test
  fun `it should serialize to (lng, lat)`() {
    val position = Position(
        lng = 1.1,
        lat = 2.2
    )

    val geojson = mapper.writeValueAsString(position)

    val expected = """
      [1.1, 2.2]
    """.replace("\\s".toRegex(), "")

    assertEquals(expected, geojson)
  }

  @Test
  fun `it should serialize to (lng, lat, alt)`() {
    val position = Position(
        lng = 1.1,
        lat = 2.2,
        alt = 3.3
    )

    val geojson = mapper.writeValueAsString(position)

    val expected = """
      [1.1, 2.2, 3.3]
    """.replace("\\s".toRegex(), "")

    assertEquals(expected, geojson)
  }

  @Test
  fun `it should round to 7th decimal for lat and lng or 2nd decimal for alt`() {
    PositionSerializer.Decimals.latlng = 7
    PositionSerializer.Decimals.altitude = 2

    val position = Position(
        lng = 1.123456789,
        lat = 2.123450009,
        alt = 3.123456
    )

    val geojson = mapper.writeValueAsString(position)

    val expected = """
        [1.1234568, 2.12345, 3.12]
      """.replace("\\s".toRegex(), "")

    assertEquals(expected, geojson)

    PositionSerializer.Decimals.latlng = Int.MAX_VALUE
    PositionSerializer.Decimals.altitude = Int.MAX_VALUE
  }

}