plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.gurbani.uiprototype"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.gurbani.uiprototype"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Core / Compose
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2024.12.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.foundation:foundation")

    // Material 3 Expressive lives in newer material3 releases (MaterialExpressiveTheme,
    // expressive shapes/motion). Pin explicitly rather than relying on the Compose BOM,
    // since Expressive APIs are newer than the BOM's baseline M3 version.
    // Check https://developer.android.com/jetpack/androidx/releases/compose-material3
    // for the current alpha/stable version before building against this.
    implementation("androidx.compose.material3:material3:1.4.0-alpha14")
    implementation("androidx.compose.material:material-icons-extended:1.7.6")

    // Blur-behind for the floating glass chrome (see design-spec.md "Blur approach").
    // Handles the RenderEffect (API 31+) vs. tonal-fallback (API 29-30) split internally.
    implementation("dev.chrisbanes.haze:haze:1.5.2")
    implementation("dev.chrisbanes.haze:haze-materials:1.5.2")

    debugImplementation("androidx.compose.ui:ui-tooling")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.12.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
