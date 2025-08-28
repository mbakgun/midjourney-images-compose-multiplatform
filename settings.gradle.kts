rootProject.name = "MidJourneyImagesComposeMultiplatform"

include(":androidApp")
include(":desktopApp")
include(":shared")
include(":wearApp")
include(":televisionApp")
include(":automotiveApp")
include(":webApp")
include(":wasmApp")

pluginManagement {
    includeBuild("gradle/build-logic")
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }

    plugins {
        val kotlinVersion = "2.2.10"
        val agpVersion = "8.12.2"
        val composeVersion = "1.8.2"

        kotlin("jvm").version(kotlinVersion)
        kotlin("multiplatform").version(kotlinVersion)
        id("org.jetbrains.kotlin.plugin.compose").version(kotlinVersion)
        id("com.android.library").version(agpVersion)
        id("org.jetbrains.compose").version(composeVersion)
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
    }
}
