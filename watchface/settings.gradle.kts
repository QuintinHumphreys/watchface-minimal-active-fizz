// Gradle settings for the Minimal Active Fizz watch face (declarative WFF).
// Single-module project: this root project IS the watch face app module.
// Mirrors the official google wear-os-samples WatchFaceFormat build config.
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "active-fizz"
