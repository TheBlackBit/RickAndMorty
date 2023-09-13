plugins {
    id("theblackbit.rickandmorty.library")
    id("theblackbit.rickandmorty.library.compose")
    id("theblackbit.rickandmorty.room")
    id("theblackbit.rickandmorty.hilt")
    id("org.jlleitschuh.gradle.ktlint")
    id("theblackbit.rickandmorty.jacoco")
}

android {
    namespace = "com.theblackbit.rickandmorty.core.localstorage"
}
