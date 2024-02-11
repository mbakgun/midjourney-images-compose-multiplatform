plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
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
            implementation(libs.imageLoader)

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
        }

        jsMain.dependencies {
            implementation(libs.okio.fakefilesystem)
            implementation(libs.ktorClientJs)
        }

        commonTest.dependencies {
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
            implementation(libs.kotlinxCoroutinesTest)
            implementation(libs.koinTest)
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
        jvmToolchain(11)
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
