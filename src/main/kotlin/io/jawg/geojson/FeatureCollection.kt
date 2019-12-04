package io.jawg.geojson

data class FeatureCollection(
    val features: List<Feature>,
    val bbox: BBox? = null
) : GeoJsonObject("FeatureCollection")