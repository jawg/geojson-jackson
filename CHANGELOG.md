# 1.3.0-SNAPSHOT
* `Feature.properties` can have null values as expected by the [RFC](https://tools.ietf.org/html/rfc7946#section-3.2)
* 3 dimensions `coordinates` don't prevent other properties to be parsed correctly

# 1.2.0 2019-12-04
* Made `GeoJsonObject` and sub-classes data class
* Upgrade kotlin to 1.3.61

# 1.1.0 2019-03-08

## Features
* Added bbox DSL with auto computation option

# 1.0.0 2019-01-03

## Features
* (De)Serialization support of GeoJson objects.
* DSL to build GeoJSON objects.