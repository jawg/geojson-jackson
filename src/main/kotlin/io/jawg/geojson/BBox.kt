package io.jawg.geojson

import io.jawg.geojson.serializer.BBoxDeserializer
import io.jawg.geojson.serializer.BBoxSerializer
import tools.jackson.databind.annotation.JsonDeserialize
import tools.jackson.databind.annotation.JsonSerialize

@JsonDeserialize(using = BBoxDeserializer::class)
@JsonSerialize(using = BBoxSerializer::class)
data class BBox(val west: Double, val south: Double, val east: Double, val north: Double)
