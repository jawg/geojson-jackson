package io.jawg.geojson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FeatureCollectionTest {

  private val mapper = ObjectMapper().registerModule(KotlinModule())

  @Test
  fun `it should serialize to FeatureCollection`() {

    val features = listOf(
        Feature(geometry = Point(Position(0.0, 10.0)), id = "point1"),
        Feature(geometry = Point(Position(0.0, 20.0)), id = "point2"),
        Feature(
            geometry = MultiLineString(
                listOf(
                    listOf(Position(0.0, 1.0), Position(1.0, 2.0)),
                    listOf(Position(0.0, 1.0), Position(1.0, 2.0), Position(2.0, 3.0))
                )
            ),
            id = "multiLineString1")
    )

    val featureCollection = FeatureCollection(features)

    val geojson = mapper.writeValueAsString(featureCollection)

    val expected = """
      {
        "features": [
          {
            "geometry": {
              "coordinates": [0.0, 10.0],
              "type": "Point"
            },
            "properties": null,
            "id": "point1",
            "type": "Feature"
          },
          {
            "geometry": {
              "coordinates": [0.0, 20.0],
              "type": "Point"
            },
            "properties": null,
            "id": "point2",
            "type": "Feature"
          },
          {
            "geometry": {
              "coordinates": [
                [[0.0, 1.0], [1.0, 2.0]],
                [[0.0, 1.0], [1.0, 2.0], [2.0, 3.0]]
              ],
              "type": "MultiLineString"
            },
            "properties": null,
            "id": "multiLineString1",
            "type": "Feature"
          }
        ],
        "type": "FeatureCollection"
      }
    """.replace("\\s".toRegex(), "")

    assertEquals(expected, geojson)
  }

  @Test
  fun `it should deserialize to FeatureCollection`() {
    val geojson = """
      {
        "features": [
          {
            "geometry": {
              "coordinates": [0.0, 10.0],
              "type": "Point"
            },
            "properties": null,
            "id": "point1",
            "type": "Feature"
          },
          {
            "geometry": {
              "coordinates": [0.0, 20.0],
              "type": "Point"
            },
            "properties": null,
            "id": "point2",
            "type": "Feature"
          },
          {
            "geometry": {
              "coordinates": [
                [[0.0, 1.0], [1.0, 2.0]],
                [[0.0, 1.0], [1.0, 2.0], [2.0, 3.0]]
              ],
              "type": "MultiLineString"
            },
            "properties": null,
            "id": "multiLineString1",
            "type": "Feature"
          }
        ],
        "type": "FeatureCollection"
      }
    """

    val featureCollection = mapper.readValue(geojson, GeoJsonObject::class.java)
    val features = listOf(
        Feature(geometry = Point(Position(0.0, 10.0)), id = "point1"),
        Feature(geometry = Point(Position(0.0, 20.0)), id = "point2"),
        Feature(
            geometry = MultiLineString(
                listOf(
                    listOf(Position(0.0, 1.0), Position(1.0, 2.0)),
                    listOf(Position(0.0, 1.0), Position(1.0, 2.0), Position(2.0, 3.0))
                )
            ),
            id = "multiLineString1")
    )

    val expected = FeatureCollection(features)

    assertTrue(featureCollection is FeatureCollection)
    assertEquals(expected.features.size, featureCollection.features.size)
  }

}