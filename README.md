# GeoJSON for Jackson

Serialize / Deserialize GeoJSON with Jackson

## Requirements
* Java 8+

## Dependency

Add the dependency in your ```dependencies { ... }```:
```kotlin
implementation("io.jawg.geojson:geojson-jackson:1.2.0-SNAPSHOT")
```

For SNAPSHOT versions add the repository in tour ```repositories { ... }```:
```kotlin
maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
```

## Control the decimal places
You can control the decimal places of the serialized latitudes, longitudes and altitudes:

```kotlin
PositionSerializer.Decimals.latlng = 7
PositionSerializer.Decimals.altitude = 2
```

## Build

```bash
./gradlew build
```