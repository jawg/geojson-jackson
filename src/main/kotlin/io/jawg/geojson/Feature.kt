package io.jawg.geojson

import com.fasterxml.jackson.annotation.JsonInclude

data class Feature(
    @JsonInclude(JsonInclude.Include.ALWAYS)
    val geometry: Geometry<*>?,
    @JsonInclude(JsonInclude.Include.ALWAYS)
    val properties: Map<String, Any>? = null,
    var id: String? = null,
    val bbox: BBox? = null
) : GeoJsonObject("Feature")