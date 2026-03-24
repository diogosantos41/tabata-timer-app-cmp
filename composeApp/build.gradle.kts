
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.androidKmpLibrary)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

kotlin {
    android {
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        namespace = libs.versions.project.applicationId.get().toString()
        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.core.splashscreen)
        }
        commonMain.dependencies {
            implementation(libs.bundles.cmp.common)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.lifecycle.runtime.compose)
            implementation(libs.compose.material3)
            implementation(libs.compose.material.icons.core)
            implementation(libs.compose.material.icons.extended)
            implementation(libs.compose.navigation)
            implementation(libs.bundles.koin.common)
            implementation(libs.datastore)
            implementation(libs.datastore.preferences)
        }
    }
}