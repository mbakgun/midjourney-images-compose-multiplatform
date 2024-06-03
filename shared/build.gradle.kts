import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    alias(libs.plugins.detekt)
    alias(libs.plugins.serialization)
}

kotlin {
    androidTarget()
    jvm()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    js(IR) {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    cocoapods {
        version = "1.0.0"
        summary = "MidJourney Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.materialIconsExtended)

            //sharedVm
            api(libs.kmmViewmodelCore)

            //di
            api(libs.koinCore)

            //network
            implementation(libs.ktorClientCore)
            implementation(libs.ktorClientJson)
            implementation(libs.ktorClientLogging)
            implementation(libs.ktorClientContentNegotiation)
            implementation(libs.ktorSerializationKotlinxJson)
            implementation(libs.kotlinxSerializationCore)

            //imageLoading
            implementation(libs.coil.compose.core)
            implementation(libs.coil.compose)
            implementation(libs.coil.mp)
            implementation(libs.coil.network.ktor)

            //coroutines
            implementation(libs.kotlinxCoroutinesCore)

            //local
            implementation(libs.multiplatformSettings)
        }

        androidMain.dependencies {
            api(libs.koin)
            implementation(libs.ktorClient)
        }

        iosMain.dependencies {
            implementation(libs.ktorClientIos)
        }

        jvmMain.dependencies {
            implementation(libs.ktorClientJvm)
            implementation(libs.ktorClientJava)
            implementation(libs.kotlinxCoroutinesSwing)
        }

        jsMain.dependencies {
            implementation(libs.ktorClientJs)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinxCoroutinesTest)
            implementation(libs.koinTest)
        }

        val wasmJsMain by getting {
            dependencies {
                implementation(libs.ktorClientWasmJs)
            }
        }

        val androidInstrumentedTest by getting {
            dependencies {
                implementation(libs.androidxUiTestJunit4)
                implementation(libs.androidxUiTestManifest)
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.mbakgun.mj.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain((findProperty("jdkVersion") as String).toInt())
    }
}

detekt {
    source.from(files(rootProject.rootDir))
    parallel = true
    autoCorrect = true
    buildUponDefaultConfig = true
}

kotlin {
    sourceSets {
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
    }
}

compose.experimental {
    web.application {
    }
}
