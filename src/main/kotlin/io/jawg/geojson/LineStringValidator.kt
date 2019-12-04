package io.jawg.geojson

internal object LineStringValidator {

  /**
   * Validate the LineString coordinates according to the RFC https://tools.ietf.org/html/rfc7946#section-3.1.4.
   * @param coordinates the [LineStringCoordinates]
   * @throws [IllegalArgumentException] if coordinates are invalid
   */
  fun validate(coordinates: LineStringCoordinates) {
    require(coordinates.size >= 2) { "LineString coordinates must have at least 2 positions" }
  }

}