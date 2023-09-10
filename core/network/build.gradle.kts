plugins {
    id("theblackbit.rickandmorty.library")
    id("theblackbit.rickandmorty.library.compose")
    id("theblackbit.rickandmorty.hilt")
    id("org.jlleitschuh.gradle.ktlint")
    id("theblackbit.rickandmorty.jacoco")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.apollographql.apollo3") version (libs.versions.apollo)
}

apollo {
    service("service") {
        packageName.set("com.theblackbit.rickandmorty")
    }
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}

android {
    namespace = "com.theblackbit.rickandmorty.core.network"
}

dependencies {
    implementation(libs.apollo.runtime)
}
