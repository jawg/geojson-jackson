package io.jawg.geojson.dsl

import io.jawg.geojson.*
import kotlin.math.max
import kotlin.math.min

internal interface Builder<T> {

  fun build(): T

}

class PropertiesBuilder : Builder<Map<String, Any>> {

  private val properties = linkedMapOf<String, Any>()

  infix fun String.to(value: Any) {
    properties[this] = value
  }

  override fun build() = properties
}

class PositionBuilder : Builder<PointCoordinates> {

  var lat: Double = 0.0
  var lng: Double = 0.0
  var alt: Double? = null

  override fun build(): PointCoordinates {
    return Position(lng, lat, alt)
  }

}

class PointBuilder : BuilderWithBBox<Point>() {

  var lat: Double = 0.0
  var lng: Double = 0.0
  var alt: Double? = null

  override fun computeBBox(): BBox = BBox(west = lng, east = lng, south = lat, north = lat)
  override fun build(): Point {
    val coordinates = PointCoordinates(lng, lat, alt)
    return Point(coordinates, bbox)
  }
}

abstract class BuilderWithBBox<T> : Builder<T> {

  private var _bbox: BBox? = null
  var bbox: BBox?
    get() = when {
      _bbox != null -> _bbox
      bboxShouldBeComputed -> computeBBox()
      else -> null
    }
    set(value) {
      _bbox = value
    }

  private var bboxShouldBeComputed = false

  fun bbox(init: BBoxBuilder.() -> Unit) {
    _bbox = BBoxBuilder().apply(init).build()
  }

  fun bbox() {
    bboxShouldBeComputed = true
  }

  abstract fun computeBBox(): BBox
}

class BBoxBuilder : Builder<BBox> {
  var west: Double? = null
  var east: Double? = null
  var north: Double? = null
  var south: Double? = null


  override fun build(): BBox = BBox(
    west = west ?: throw IllegalArgumentException("west must be specified"),
    east = east ?: throw IllegalArgumentException("east must be specified"),
    north = north ?: throw IllegalArgumentException("north must be specified"),
    south = south ?: throw IllegalArgumentException("south must be specified")
  )
}

class LineStringBuilder : BuilderWithBBox<LineString>() {

  val coordinates = mutableListOf<Position>()

  fun position(init: PositionBuilder.() -> Unit): Position {
    val builder = PositionBuilder()
    builder.init()
    val position = builder.build()
    coordinates += position
    return position
  }

  override fun computeBBox(): BBox = coordinates.toBBox()
  override fun build(): LineString {
    return LineString(coordinates, bbox)
  }
}

class LinearRingBuilder : Builder<LinearRing> {

  val coordinates = mutableListOf<Position>()

  fun position(init: PositionBuilder.() -> Unit): Position {
    val builder = PositionBuilder()
    builder.init()
    val position = builder.build()
    coordinates += position
    return position
  }

  override fun build() = coordinates
}

class PolygonBuilder : BuilderWithBBox<Polygon>() {

  val rings = mutableListOf<LinearRing>()

  fun ring(init: LinearRingBuilder.() -> Unit): LinearRing {
    val builder = LinearRingBuilder()
    builder.init()
    val ring = builder.build()
    rings += ring
    return ring
  }

  override fun computeBBox(): BBox = rings.flatten().toBBox()
  override fun build(): Polygon {
    return Polygon(rings, bbox)
  }
}

class MultiPointBuilder : BuilderWithBBox<MultiPoint>() {

  val coordinates = mutableListOf<Position>()

  fun position(init: PositionBuilder.() -> Unit): Position {
    val builder = PositionBuilder()
    builder.init()
    val position = builder.build()
    coordinates += position
    return position
  }

  override fun computeBBox(): BBox = coordinates.toBBox()
  override fun build(): MultiPoint {
    return MultiPoint(coordinates, bbox)
  }
}

class MultiLineStringBuilder : BuilderWithBBox<MultiLineString>() {

  val lineStrings = mutableListOf<LineString>()

  fun lineString(init: LineStringBuilder.() -> Unit): LineString {
    val lineString = io.jawg.geojson.dsl.lineString(init)
    lineStrings += lineString
    return lineString
  }

  override fun computeBBox(): BBox = lineStrings.flatMap { it.getAllCoordinates() }.toBBox()
  override fun build(): MultiLineString {
    return MultiLineString(lineStrings.mapNotNull(LineString::coordinates), bbox)
  }
}

class MultiPolygonBuilder : BuilderWithBBox<MultiPolygon>() {

  val polygons = mutableListOf<Polygon>()

  fun polygon(init: PolygonBuilder.() -> Unit): Polygon {
    val polygon = io.jawg.geojson.dsl.polygon(init)
    polygons += polygon
    return polygon
  }

  override fun computeBBox(): BBox = polygons.flatMap { it.getAllCoordinates() }.toBBox()
  override fun build(): MultiPolygon {
    return MultiPolygon(polygons.mapNotNull(Polygon::coordinates), bbox)
  }
}

class GeometryCollectionBuilder : BuilderWithBBox<GeometryCollection>() {

  val geometries = mutableListOf<Geometry<*>>()

  fun point(init: PointBuilder.() -> Unit): Point {
    val point = io.jawg.geojson.dsl.point(init)
    geometries += point
    return point
  }

  fun multiPoint(init: MultiPointBuilder.() -> Unit): MultiPoint {
    val multiPoint = io.jawg.geojson.dsl.multiPoint(init)
    geometries += multiPoint
    return multiPoint
  }

  fun lineString(init: LineStringBuilder.() -> Unit): LineString {
    val lineString = io.jawg.geojson.dsl.lineString(init)
    geometries += lineString
    return lineString
  }

  fun multiLineString(init: MultiLineStringBuilder.() -> Unit): MultiLineString {
    val multiLineString = io.jawg.geojson.dsl.multiLineString(init)
    geometries += multiLineString
    return multiLineString
  }

  fun polygon(init: PolygonBuilder.() -> Unit): Polygon {
    val polygon = io.jawg.geojson.dsl.polygon(init)
    geometries += polygon
    return polygon
  }

  fun multiPolygon(init: MultiPolygonBuilder.() -> Unit): MultiPolygon {
    val multiPolygon = io.jawg.geojson.dsl.multiPolygon(init)
    geometries += multiPolygon
    return multiPolygon
  }

  override fun computeBBox(): BBox = geometries.flatMap { it.getAllCoordinates() }.toBBox()
  override fun build(): GeometryCollection {
    return GeometryCollection(geometries, bbox)
  }
}

class FeatureBuilder : BuilderWithBBox<Feature>() {

  var id: String? = null
  private lateinit var geometry: Geometry<*>
  private var properties: Map<String, Any>? = null

  fun point(init: PointBuilder.() -> Unit): Point {
    val point = io.jawg.geojson.dsl.point(init)
    geometry = point
    return point
  }

  fun multiPoint(init: MultiPointBuilder.() -> Unit): MultiPoint {
    val multiPoint = io.jawg.geojson.dsl.multiPoint(init)
    geometry = multiPoint
    return multiPoint
  }

  fun lineString(init: LineStringBuilder.() -> Unit): LineString {
    val lineString = io.jawg.geojson.dsl.lineString(init)
    geometry = lineString
    return lineString
  }

  fun multiLineString(init: MultiLineStringBuilder.() -> Unit): MultiLineString {
    val multiLineString = io.jawg.geojson.dsl.multiLineString(init)
    geometry = multiLineString
    return multiLineString
  }

  fun polygon(init: PolygonBuilder.() -> Unit): Polygon {
    val polygon = io.jawg.geojson.dsl.polygon(init)
    geometry = polygon
    return polygon
  }

  fun multiPolygon(init: MultiPolygonBuilder.() -> Unit): MultiPolygon {
    val multiPolygon = io.jawg.geojson.dsl.multiPolygon(init)
    geometry = multiPolygon
    return multiPolygon
  }

  fun geometryCollection(init: GeometryCollectionBuilder.() -> Unit): GeometryCollection {
    val geometryCollection = io.jawg.geojson.dsl.geometryCollection(init)
    geometry = geometryCollection
    return geometryCollection
  }

  fun properties(init: PropertiesBuilder.() -> Unit): Map<String, Any> {
    val builder = PropertiesBuilder()
    builder.init()
    return builder.build()
      .also { properties = it }
  }

  override fun computeBBox(): BBox = geometry.getAllCoordinates().toBBox()
  override fun build(): Feature {
    return Feature(geometry, properties, id, bbox)
  }
}

class FeatureCollectionBuilder : BuilderWithBBox<FeatureCollection>() {

  val features = mutableListOf<Feature>()

  fun feature(init: FeatureBuilder.() -> Unit): Feature {
    val feature = io.jawg.geojson.dsl.feature(init)
    features += feature
    return feature
  }

  override fun computeBBox(): BBox = features.flatMap { it.geometry?.getAllCoordinates().orEmpty() }.toBBox()
  override fun build(): FeatureCollection {
    return FeatureCollection(features, bbox)
  }
}


fun point(init: PointBuilder.() -> Unit): Point {
  val builder = PointBuilder()
  builder.init()
  return builder.build()
}

fun multiPoint(init: MultiPointBuilder.() -> Unit): MultiPoint {
  val builder = MultiPointBuilder()
  builder.init()
  return builder.build()
}

fun lineString(init: LineStringBuilder.() -> Unit): LineString {
  val builder = LineStringBuilder()
  builder.init()
  return builder.build()
}

fun multiLineString(init: MultiLineStringBuilder.() -> Unit): MultiLineString {
  val builder = MultiLineStringBuilder()
  builder.init()
  return builder.build()
}

fun polygon(init: PolygonBuilder.() -> Unit): Polygon {
  val builder = PolygonBuilder()
  builder.init()
  return builder.build()
}

fun multiPolygon(init: MultiPolygonBuilder.() -> Unit): MultiPolygon {
  val builder = MultiPolygonBuilder()
  builder.init()
  return builder.build()
}

fun geometryCollection(init: GeometryCollectionBuilder.() -> Unit): GeometryCollection {
  val builder = GeometryCollectionBuilder()
  builder.init()
  return builder.build()
}

fun feature(init: FeatureBuilder.() -> Unit): Feature {
  val builder = FeatureBuilder()
  builder.init()
  return builder.build()
}

fun featureCollection(init: FeatureCollectionBuilder.() -> Unit): FeatureCollection {
  val builder = FeatureCollectionBuilder()
  builder.init()
  return builder.build()
}

fun List<Position>.toBBox(): BBox {
  val positions = this
  require(positions.isNotEmpty()) { "positions should be non empty" }

  var minLat = Double.POSITIVE_INFINITY
  var maxLat = Double.NEGATIVE_INFINITY
  var minLng = Double.POSITIVE_INFINITY
  var maxLng = Double.NEGATIVE_INFINITY
  positions.forEach { position ->
    minLat = min(minLat, position.lat)
    maxLat = max(maxLat, position.lat)
    minLng = min(minLng, position.lng)
    maxLng = max(maxLng, position.lng)
  }

  return BBox(
    west = minLng,
    east = maxLng,
    north = maxLat,
    south = minLat
  )
}
