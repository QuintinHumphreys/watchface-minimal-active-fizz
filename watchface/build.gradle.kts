// Build config for the Minimal Active Fizz watch face (declarative WFF - no Kotlin/Java).
// A WFF face is an Android app with android:hasCode="false" whose only payload
// is res/raw/watchface.xml. Config mirrors the official google wear-os-samples
// WatchFaceFormat sample (AGP 9.0.0, Gradle 9.2.1).
plugins {
    alias(libs.plugins.android.application)
}

android {
    enableKotlin = false
    namespace = "com.quintinhumphreys.watchfaces.activefizz"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.quintinhumphreys.watchfaces.activefizz"
        minSdk = 33          // Wear OS 4+ (WFF format v1); target device is Wear OS 6
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            // Debug signing so a release build is installable for local testing.
            // Replace with a real signingConfig before any Play upload.
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}
