import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    jvm {
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.mbakgun.mj.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageVersion = "1.0.0"
            copyright = "Mbakgun"
            description = "MjImagesDesktopApp"
            packageName = "MjImagesDesktopApp"

            macOS {
                dockName = "MjImagesDesktopApp"
                appCategory = "public.app-category.utilities"
            }
        }
    }
}
