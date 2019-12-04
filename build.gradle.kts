import org.jetbrains.kotlin.backend.common.onlyIf

plugins {
  maven
  `maven-publish`
  signing
  kotlin("jvm") version "1.3.61"
}

description = "GeoJSON for Jackson"

group = "io.jawg.geojson"
version = "1.2.0"

val isReleaseVersion = !version.toString().endsWith("SNAPSHOT")

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
  compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }

  compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }

  java {
    withJavadocJar()
    withSourcesJar()
  }

  javadoc {
    if (JavaVersion.current().isJava9Compatible) {
      (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
  }
}

repositories {
  mavenCentral()
}

publishing {
  repositories {
    maven {
      val releasesRepoUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2"
      val snapshotsRepoUrl = "https://oss.sonatype.org/content/repositories/snapshots"
      url = uri(if (isReleaseVersion) releasesRepoUrl else snapshotsRepoUrl)
      credentials {
        val ossrhUsername: String? by project
        val ossrhPassword: String? by project
        username = ossrhUsername
        password = ossrhPassword
      }
    }
  }
  publications {
    create<MavenPublication>("mavenJava") {
      from(components["java"])
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
  }
}

signing {
  onlyIf({ isReleaseVersion }) {
    sign(publishing.publications["mavenJava"])
  }
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("reflect"))
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.1")
  testImplementation(kotlin("test"))
  testImplementation(kotlin("test-junit"))
  testImplementation("org.skyscreamer:jsonassert:1.5.0")
}