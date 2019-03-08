package io.jawg.geojson

class FeatureCollection(
    val features: List<Feature>,
    val bbox: BBox? = null
) : GeoJsonObject("FeatureCollection")