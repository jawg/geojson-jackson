rootProject.name = "geojson-jackson"

pluginManagement {
  plugins {
    kotlin("jvm") version "${extra["version.kotlin"]}"
    id("com.github.ben-manes.versions") version "${extra["version.versions.plugin"]}"
  }
}

dependencyResolutionManagement {
  repositories {
    mavenCentral()
  }
}
