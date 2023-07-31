plugins {
  `java-library`
  `maven-publish`
  id("io.freefair.lombok") version "8.1.0"
}

group = "by.sws.core"
version = libs.versions.sdk.get()

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(8))
  }
  withSourcesJar()
}

tasks.withType<JavaCompile>().configureEach {
  options.isFork = true
}


dependencies {

  // consuming platform helps for automatic management
  // your will get versions from dependency constraints
  api(platform("com.google:sdk-demo:${libs.versions.sdk.get()}"))

  // This dependency is exported to consumers, that is to say found on their compile classpath.
  api("org.apache.commons:commons-math3:3.6.1")

  // This dependency is used internally, and not exposed to consumers on their own compile classpath.
  implementation("com.google.guava:guava:31.1-jre")

  testImplementation(libs.bundles.junit)
}

tasks.withType<Test> {
  useJUnitPlatform()
  systemProperty("junit.jupiter.extensions.autodetection.enabled", true)
  systemProperty("junit.jupiter.execution.parallel.enabled", true)
  systemProperty("junit.jupiter.execution.parallel.mode.default", "concurrent")
  systemProperty("junit.jupiter.execution.parallel.mode.classes.default", "concurrent")
}

tasks.withType<Test>().configureEach {
  maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
}

publishing {
  publications {
    create<MavenPublication>("swsLibrary") {
      groupId = project.group.toString()
      artifactId = rootProject.name
      version = project.version.toString()
      from(components["java"])
    }
  }
}
