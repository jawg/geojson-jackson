package io.jawg.geojson

class FeatureCollection(
    val features: List<Feature>,
    val bbox: List<Double>? = null
) : GeoJsonObject("FeatureCollection")