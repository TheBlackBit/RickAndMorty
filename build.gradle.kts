plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.apollo3) apply false
    alias(libs.plugins.secrets.gradle.plugin) apply false
}

buildscript {
    dependencies {
        classpath(libs.ktlint.gradle)
    }
}
