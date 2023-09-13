import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport

class AndroidJacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.gradle.jacoco")
            }
            configureJacoco()
        }
    }
}

private val coverageExclusions = listOf(
    "**/R.class",
    "**/R\$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
)

private fun Project.configureJacoco() {
    extensions.getByType<JacocoPluginExtension>().run {
        toolVersion = "0.8.8"
    }

    tasks.create("jacocoMergedReport", JacocoReport::class.java) {
        dependsOn("testDebugUnitTest")

        reports {
            xml.required.set(true)
            html.required.set(true)
        }

        val kotlinTree = fileTree("$buildDir/tmp/kotlin-classes/debug") {
            coverageExclusions
        }.filter {
            !it.name.contains("Query") &&
                    !it.name.contains("GraphQL") &&
                    !it.name.contains("Module") &&
                    !it.name.contains("Composable") &&
                    !it.name.contains("Screen") &&
                    !it.name.contains("Graph")


        }

        classDirectories.from(kotlinTree)

        sourceDirectories.setFrom(files("$projectDir/src/main/java"))

        executionData.setFrom(
            fileTree(project.buildDir) {
                include(
                    "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec"
                )
            }
        )
    }

    tasks.withType<Test>().run {
        all {
            configure<JacocoTaskExtension> {
                isIncludeNoLocationClasses = true
                excludes = listOf("jdk.internal.*")
            }
        }
    }
}
