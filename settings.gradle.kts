rootProject.name = "geojson-jackson"

pluginManagement {
  plugins {
    kotlin("jvm") version "${extra["version.kotlin"]}"
    id("com.github.ben-manes.versions") version "${extra["version.versions.plugin"]}"
    id("com.vanniktech.maven.publish") version "${extra["version.maven-publish.plugin"]}"
  }
}

dependencyResolutionManagement {
  repositories {
    mavenCentral()
  }
}
