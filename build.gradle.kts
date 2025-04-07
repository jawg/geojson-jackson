import com.vanniktech.maven.publish.SonatypeHost

plugins {
  kotlin("jvm")
  id("com.github.ben-manes.versions")
  id("com.vanniktech.maven.publish")
}

description = "GeoJSON for Jackson"

group = "io.jawg.geojson"
version = "${property("version")}"

kotlin {
  jvmToolchain(21)
}

mavenPublishing {
  publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
  signAllPublications()

  pom {
    name.set("GeoJSON Jackson for Kotlin")
    description.set("(De)Serialization of GeoJSON with Jackson for Kotlin")
    url.set("https://github.com/jawg/geojson-jackson")
    licenses {
      license {
        name.set("The Apache License, Version 2.0")
        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
      }
    }
    organization {
      name.set("Jawg")
      url.set("https://jawg.io")
    }
    developers {
      developer {
        id.set("jawg")
        name.set("Jawg")
        email.set("contact@jawg.io")
      }
    }
    scm {
      connection.set("scm:git:git://git@github.com:jawg/geojson-jackson.git")
      developerConnection.set("scm:git:ssh://git@github.com:jawg/geojson-jackson.git")
      url.set("https://github.com/jawg/geojson-jackson")
    }
  }
}

dependencies {
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${property("version.jackson")}")
  testImplementation(kotlin("test-junit"))
  testImplementation("org.skyscreamer:jsonassert:${property("version.jsonassert")}")
}
