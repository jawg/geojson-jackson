package io.jawg.geojson.utils

import io.jawg.geojson.*
import io.jawg.geojson.dsl.toBBox

object GeoJsonFactory {

  fun point(alt: Boolean): Point {
    val coordinates = Position(100.0, 0.0, 10.0.takeIf { alt })
    return Point(coordinates)
  }

  fun lineString(): LineString {
    val coordinates = listOf(
      Position(100.0, 0.0),
      Position(101.0, 1.0)
    )

    return LineString(coordinates)
  }

  fun geometryCollection(): GeometryCollection {
    val pointCoordinates = Position(100.0, 0.0)
    val point = Point(pointCoordinates)

    val lineStringCoordinates = listOf(
      Position(101.0, 0.0),
      Position(102.0, 1.0)
    )
    val lineString = LineString(lineStringCoordinates)

    return GeometryCollection(listOf(point, lineString))
  }

  fun feature(id: Boolean = false, alt: Boolean = false, properties: Boolean = false, bbox: Boolean = false): Feature {
    val featureId = if (id) "p1" else null
    val point = point(alt)
    val featureProperties = if (properties) {
      mapOf(
        "p1" to "hello",
        "p2" to 3,
        "p3" to mapOf("q1" to "world"),
        "p4" to null
      )
    } else {
      emptyMap()
    }
    val featureBbox = if (bbox) point.getAllCoordinates().toBBox() else null
    return Feature(point, featureProperties, featureId, featureBbox)
  }

}
