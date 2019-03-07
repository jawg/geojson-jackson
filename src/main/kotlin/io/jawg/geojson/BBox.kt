package io.jawg.geojson

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.jawg.geojson.serializer.BBoxDeserializer
import io.jawg.geojson.serializer.BBoxSerializer

@JsonDeserialize(using = BBoxDeserializer::class)
@JsonSerialize(using = BBoxSerializer::class)
data class BBox(val west: Double, val south: Double, val east: Double, val north: Double)
