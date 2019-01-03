plugins {
  maven
  `maven-publish`
  signing
  kotlin("jvm") version "1.3.11"
}

description = "GeoJSON for Jackson"

group = "io.jawg.geojson"
version = "1.0.0-SNAPSHOT"

tasks {
  compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }
  compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }
}

task<Jar>("sourcesJar") {
  from(sourceSets.main.get().allJava)
  classifier = "sources"
}

task<Jar>("javadocJar") {
  from(tasks.javadoc)
  classifier = "javadoc"
}

tasks.javadoc {
  if (JavaVersion.current().isJava9Compatible) {
    (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
  }
}

repositories {
  mavenCentral()
}

publishing {
  repositories {
    maven {
      url = uri("$buildDir/repo")
    }
  }
  publications {
    create<MavenPublication>("mavenJava") {
      from(components["java"])
      artifact(tasks["sourcesJar"])
      artifact(tasks["javadocJar"])
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
  sign(publishing.publications["mavenJava"])
}

dependencies {
  compile(kotlin("stdlib-jdk8"))
  compile(kotlin("reflect"))
  compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
  testCompile(kotlin("test"))
  testCompile(kotlin("test-junit"))
  testCompile("org.skyscreamer:jsonassert:1.5.0")
}