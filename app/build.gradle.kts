plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")


}

android {
    namespace = "com.paltech.ontheshelf_prod"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.paltech.ontheshelf_prod"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation ("androidx.core:core-splashscreen:1.0.1")

    //Compose Navigation
    implementation ("androidx.navigation:navigation-compose:2.7.7")

    //Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-compiler:2.52")

    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation ("androidx.work:work-runtime-ktx:2.7.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")

    //Datastore
    implementation( "androidx.datastore:datastore-preferences:1.0.0")

    //Compose Foundation
    implementation( "androidx.compose.foundation:foundation:1.4.3")

    //Accompanist
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.31.4-beta")
    implementation ("androidx.compose.ui:ui:1.6.8")

    //Paging 3
    implementation(libs.androidx.paging.runtime)
    implementation ("androidx.paging:paging-compose:3.2.0-rc01")
    implementation ("androidx.room:room-runtime:2.5.0")
    kapt ("androidx.room:room-compiler:2.5.0")
    implementation ("androidx.room:room-ktx:2.5.0")


    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation ("androidx.compose.material3:material3:1.2.1")
}

kapt {
    correctErrorTypes = true
}