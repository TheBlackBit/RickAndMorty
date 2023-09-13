plugins {
    id("theblackbit.rickandmorty.library")
    id("theblackbit.rickandmorty.library.compose")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    namespace = "com.theblackbit.rickandmorty.core.resources"
}

dependencies {
    implementation(libs.core.splashscreen)
}
