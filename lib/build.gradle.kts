plugins {
  `java-library`
  `maven-publish`
  id("io.freefair.lombok") version "8.1.0"
}

group = "com.google"
version = libs.versions.sdk.get()

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(8))
  }
  withSourcesJar()
}

// https://docs.gradle.org/current/userguide/performance.html#run_the_compiler_as_a_separate_process
tasks.withType<JavaCompile>().configureEach {
  options.isFork = true
}


dependencies {
  // This dependency is exported to consumers, that is to say found on their compile classpath.
  api("org.apache.commons:commons-math3:3.6.1")

  // This dependency is used internally, and not exposed to consumers on their own compile classpath.
  implementation("com.google.guava:guava:31.1-jre")

  // Example of consuming library bundle (set of dependencies that are going together)
  testImplementation(libs.bundles.junit)
}

tasks.withType<Test> {
  useJUnitPlatform()
  // https://junit.org/junit5/docs/snapshot/user-guide/#writing-tests-parallel-execution
  systemProperty("junit.jupiter.extensions.autodetection.enabled", true)
  systemProperty("junit.jupiter.execution.parallel.enabled", true)
  systemProperty("junit.jupiter.execution.parallel.mode.default", "concurrent")
  systemProperty("junit.jupiter.execution.parallel.mode.classes.default", "concurrent")
}

// Set to 1 exactly if you have integration tests that are going to reuse testcontainers
// Set to availableProcessors() / 2 for functional test for maximum performance
// Read more https://docs.gradle.org/current/userguide/performance.html#execute_tests_in_parallel
tasks.withType<Test>().configureEach {
  maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
}

publishing {
  publications {
    create<MavenPublication>("googleRepository") {
      groupId = project.group.toString()
      artifactId = rootProject.name
      version = project.version.toString()
      from(components["java"])
    }
  }
}