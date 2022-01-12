package io.jawg.geojson.serializer

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.jawg.geojson.BBox
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BBoxDeserializerTest {

  private val mapper = jacksonObjectMapper()

  @Test
  fun `it should deserialize to BBox with Double`() {
    val geojson = "[1.0, 2.0, 3.0, 4.0]"
    val expectedBBox = BBox(1.0, 2.0, 3.0, 4.0)

    val bbox = mapper.readValue(geojson, BBox::class.java)
    assertEquals(expectedBBox, bbox)
  }

  @Test
  fun `it should deserialize to BBox with Int`() {
    val geojson = "[1, 2, 3, 4]"
    val expectedBBox = BBox(1.0, 2.0, 3.0, 4.0)

    val bbox = mapper.readValue(geojson, BBox::class.java)
    assertEquals(expectedBBox, bbox)
  }

  @Test
  fun `it should deserialize to BBox with String`() {
    val geojson = """["1.0", "2.0", "3.0", "4.0"]"""
    val expectedBBox = BBox(1.0, 2.0, 3.0, 4.0)

    val bbox = mapper.readValue(geojson, BBox::class.java)
    assertEquals(expectedBBox, bbox)
  }

  @Test
  fun `it should ignore more than 4 elements`() {
    val geojson = """["1.0", "2.0", "3.0", "4.0", "5.0"]"""
    val expectedBBox = BBox(1.0, 2.0, 3.0, 4.0)

    val bbox = mapper.readValue(geojson, BBox::class.java)
    assertEquals(expectedBBox, bbox)
  }

  @Test
  fun `it should ignore fail with less than 4 elements`() {
    val geojson = """["1.0", "2.0", "3.0"]"""

    assertFailsWith(MismatchedInputException::class) {
      mapper.readValue(geojson, BBox::class.java)
    }
  }
}
