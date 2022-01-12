package io.jawg.geojson.serializer

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.jawg.geojson.BBox
import org.junit.Test
import org.skyscreamer.jsonassert.JSONAssert

class BBoxSerializerTest {

  private val mapper = jacksonObjectMapper()

  @Test
  fun `it should serialize to BBox`() {
    val bbox = BBox(1.1, 2.2, 3.3, 4.4)
    val geojson = mapper.writeValueAsString(bbox)

    val expected = """
      [1.1, 2.2, 3.3, 4.4]
    """

    JSONAssert.assertEquals(expected, geojson, true)
  }
}
