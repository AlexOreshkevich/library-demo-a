rootProject.name = "library-demo-a"
include("lib")

dependencyResolutionManagement {

  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

  repositories {
    mavenLocal()
    mavenCentral()
    // add your corporate repo's here
    /*
    maven {
        url = uri("https://repo.google.com/repository/internal")
        mavenContent {
            releasesOnly()
        }
    }
    maven {
        url = uri("https://repo.google.com/repository/snapshots")
        mavenContent {
            snapshotsOnly()
        }
    }*/
  }

  versionCatalogs {
    create("libs") {
      from("com.google:version-catalog:DEV-SNAPSHOT")
    }
  }
}