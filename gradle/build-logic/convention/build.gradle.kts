plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies{
    compileOnly(libs.build.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.compiler.plugin)
}

gradlePlugin{
    plugins{
        register("androidTarget"){
            id = "midjourney.target.android"
            implementationClass = "AndroidTargetPlugin"
        }
    }
}
