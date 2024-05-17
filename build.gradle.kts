// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.4.0") // Asegúrate de que esta versión sea la correcta
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0") // Actualiza según sea necesario
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
        // Otras dependencias aquí...
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

task clean(type: Delete) {
    delete(rootProject.buildDir)
}
