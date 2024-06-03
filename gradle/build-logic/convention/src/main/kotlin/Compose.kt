import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

fun Project.composeCompiler(block: ComposeCompilerGradlePluginExtension.() -> Unit) {
    extensions.configure<ComposeCompilerGradlePluginExtension>(block)
}
fun Project.configureCompose() {
    composeCompiler {
        enableStrongSkippingMode.set(true)

        includeSourceInformation.set(true)

        if (project.providers.gradleProperty("midjourney.enableComposeCompilerReports").isPresent) {
            val composeReports = layout.buildDirectory.map { it.dir("reports").dir("compose") }
            reportsDestination.set(composeReports)
            metricsDestination.set(composeReports)
        }
    }
}