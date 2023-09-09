import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.theblackbit.rickandmorty.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.gradlePlugin.kotlin)
    compileOnly(libs.android.tools.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "theblackbit.rickandmorty.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "theblackbit.rickandmorty.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidHilt") {
            id = "theblackbit.rickandmorty.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidPaging") {
            id = "theblackbit.rickandmorty.paging"
            implementationClass = "AndroidPagingConventionPlugin"
        }
        register("androidRoom") {
            id = "theblackbit.rickandmorty.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("androidLibrary") {
            id = "theblackbit.rickandmorty.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "theblackbit.rickandmorty.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
    }
}
