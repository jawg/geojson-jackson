# GeoJSON for Jackson

Serialize / Deserialize GeoJSON with Jackson

## Requirements
* Java 8+

## Dependency

Add the dependency in your ```dependencies { ... }```:
```kotlin
implementation("io.jawg.geojson:geojson-jackson:1.3.0-SNAPSHOT")
```

For SNAPSHOT versions add the repository in your ```repositories { ... }```:
```kotlin
maven { url = uri("https://central.sonatype.com/repository/maven-snapshots/") }
```

## Build

```bash
./gradlew build
```
