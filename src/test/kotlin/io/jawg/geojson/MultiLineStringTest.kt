package io.jawg.geojson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.jawg.geojson.utils.GeoJsonLoader
import org.junit.Test
import org.skyscreamer.jsonassert.JSONAssert
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MultiLineStringTest {

  private val mapper = ObjectMapper().registerModule(KotlinModule())

  @Test
  fun `it should deserialize to MultiLineString`() {
    val geojson = GeoJsonLoader.loadMultiLineString("2-lines.json")

    val multiLineString: MultiLineString = mapper.readValue(geojson, GeoJsonObject::class.java) as MultiLineString

    assertEquals(2, multiLineString.coordinates!!.size)
  }

  @Test
  fun `it should serialize to MultiLineString`() {
    val line1 = listOf(
        Position(100.0, 0.0),
        Position(101.0, 1.0)
    )
    val line2 = listOf(
        Position(102.0, 2.0),
        Position(103.0, 3.0)
    )

    val multiLineString = MultiLineString(listOf(line1, line2))

    val geojson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(multiLineString)

    val expected = GeoJsonLoader.loadMultiLineString("2-lines.json")

    JSONAssert.assertEquals(expected, geojson, false)
  }

  @Test
  fun `it should fail when line coordinates count is less than 2`() {
    val line1 = listOf(
        Position(100.0, 0.0),
        Position(101.0, 1.0)
    )
    val line2 = listOf(
        Position(102.0, 2.0)
    )

    assertFailsWith(IllegalArgumentException::class) {
      MultiLineString(listOf(line1, line2))
    }
  }
}
