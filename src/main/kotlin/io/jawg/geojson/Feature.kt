package io.jawg.geojson

import com.fasterxml.jackson.annotation.JsonInclude

data class Feature(
    @field:JsonInclude(JsonInclude.Include.ALWAYS)
    val geometry: Geometry<*>?,
    @field:JsonInclude(JsonInclude.Include.ALWAYS)
    val properties: Map<String, Any?>? = null,
    var id: String? = null,
    val bbox: BBox? = null
) : GeoJsonObject("Feature")