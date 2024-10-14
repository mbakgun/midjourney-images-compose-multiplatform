import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

fun Project.composeCompiler(block: ComposeCompilerGradlePluginExtension.() -> Unit) {
    extensions.configure<ComposeCompilerGradlePluginExtension>(block)
}

fun Project.configureCompose() {
    composeCompiler {
        featureFlags.add(ComposeFeatureFlag.StrongSkipping)

        includeSourceInformation.set(true)

        if (project.providers.gradleProperty("midjourney.enableComposeCompilerReports").isPresent) {
            val composeReports = layout.buildDirectory.map { it.dir("reports").dir("compose") }
            reportsDestination.set(composeReports)
            metricsDestination.set(composeReports)
        }
    }
}
