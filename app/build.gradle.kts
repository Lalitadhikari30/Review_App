plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.reviewapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.reviewapp"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Core Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose BOM - ensures all compose libraries use compatible versions
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")

    implementation ("com.google.firebase:firebase-firestore-ktx:25.1.0")



//    // Navigation
//    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Extended Material Icons (for the onboarding screen)
    implementation("androidx.compose.material:material-icons-extended:1.6.1")
    implementation(libs.car.ui.lib)
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation(libs.firebase.auth)

    implementation("androidx.compose.runtime:runtime-livedata:1.8.3")
    implementation(libs.firebase.firestore)

    implementation ("com.google.firebase:firebase-storage-ktx:21.0.2")
    implementation(libs.firebase.storage)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)


    //Nav Controller
    val nav_version = "2.9.2"

    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("androidx.compose.foundation:foundation:1.5.0")
// Use the version that matches your Compose version

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug dependencies
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}