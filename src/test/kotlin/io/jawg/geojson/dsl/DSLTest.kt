package io.jawg.geojson.dsl

import io.jawg.geojson.BBox
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DSLTest {
  @Test
  fun `it should generate a bbox correctly`() {
    val featureCollection = featureCollection {
      bbox()
      feature {
        lineString {
          bbox()
          position { lng = 25.4; lat = -45.0 }
          position { lng = 35.4; lat = 65.0 }
        }
      }
      feature {
        bbox()
        lineString {
          position { lng = 1.0; lat = 1.0 }
          position { lng = 10.0; lat = 15.0 }
        }
      }
      feature {
        bbox()
        point {
          bbox { west = -50.0; east = 55.5; south = -45.0; north = 48.0 }
          lng = -2.0
          lat = 50.0
        }
      }
    }

    val expectedTopBBox = BBox(west = -2.0, east = 35.4, south = -45.0, north = 65.0)
    assertEquals(expectedTopBBox, featureCollection.bbox)

    val expectedFirstFeatureGeometryBBox = BBox(west = 25.4, east = 35.4, south = -45.0, north = 65.0)
    assertNull(featureCollection.features.first().bbox)
    assertEquals(expectedFirstFeatureGeometryBBox, featureCollection.features.first().geometry?.bbox)

    val expectedSecondFeatureBBox = BBox(west = 1.0, south = 1.0, east = 10.0, north = 15.0)
    assertEquals(expectedSecondFeatureBBox, featureCollection.features[1].bbox)
    assertNull(featureCollection.features[1].geometry?.bbox)

    val expectedLastFeatureBBox = BBox(west = -2.0, south = 50.0, east = -2.0, north = 50.0)
    assertEquals(expectedLastFeatureBBox, featureCollection.features.last().bbox)

    val expectedPointBBox = BBox(west = -50.0, east = 55.5, south = -45.0, north = 48.0)
    assertEquals(expectedPointBBox, featureCollection.features.last().geometry?.bbox)
  }
}
