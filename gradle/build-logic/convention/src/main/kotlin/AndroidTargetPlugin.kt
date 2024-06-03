import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class AndroidTargetPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.kotlin {
            androidTarget()
            sourceSets.androidMain.dependencies {
                implementation(project(":shared"))
                api("androidx.activity:activity-compose:1.9.0")
                api("androidx.appcompat:appcompat:1.7.0")
                api("androidx.core:core-ktx:1.13.1")
            }
        }
        target.pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
        target.configureAndroid()
        target.configureCompose()
    }
}

fun Project.android(action: BaseAppModuleExtension.() -> Unit) =
    extensions.configure<BaseAppModuleExtension>(action)

fun Project.kotlin(action: KotlinMultiplatformExtension.() -> Unit) =
    extensions.getByType(KotlinMultiplatformExtension::class.java).action()

fun Project.configureAndroid() {
    android {
        compileSdk = (findProperty("android.compileSdk") as String).toInt()
        namespace = "com.mbakgun.mj"

        sourceSets["main"].manifest.srcFile("src/main/AndroidManifest.xml")

        defaultConfig {
            applicationId = "com.mbakgun.mj"
            minSdk = (findProperty("android.minSdk") as String).toInt()
            targetSdk = (findProperty("android.targetSdk") as String).toInt()
            versionCode = 1
            versionName = "1.0"
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        kotlin {
            jvmToolchain(11)
        }
    }
}