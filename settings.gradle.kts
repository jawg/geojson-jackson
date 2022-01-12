rootProject.name = "geojson-jackson"

pluginManagement {
  plugins {
    kotlin("jvm") version "${extra["version.kotlin"]}"
  }
}

dependencyResolutionManagement {
  repositories {
    mavenCentral()
  }
}

