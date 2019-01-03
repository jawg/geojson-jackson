package io.jawg.geojson

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(property = "type", use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonSubTypes(
    JsonSubTypes.Type(Point::class),
    JsonSubTypes.Type(MultiPoint::class),
    JsonSubTypes.Type(LineString::class),
    JsonSubTypes.Type(MultiLineString::class),
    JsonSubTypes.Type(Polygon::class),
    JsonSubTypes.Type(MultiPolygon::class),
    JsonSubTypes.Type(GeometryCollection::class),
    JsonSubTypes.Type(Feature::class),
    JsonSubTypes.Type(FeatureCollection::class)
)
@JsonInclude(JsonInclude.Include.NON_NULL)
abstract class GeoJsonObject(
    val type: String
)