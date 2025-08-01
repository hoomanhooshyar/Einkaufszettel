plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger)
    alias(libs.plugins.kotlinSerialization)
    id("com.google.gms.google-services")
}

/*kapt {
    correctErrorTypes = true
}*/

android {
    namespace = "com.hooman.einkaufszettel"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hooman.einkaufszettel"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    //این بلاک کد برای حذف مقادیر تکراری در تعریف کتابخانه ها اضافه شده است
    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
}

hilt {
    enableAggregatingTask = false
}

dependencies {


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.lifecycle.viewmodel.compose)


    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))

    //Firebase Authentication
    implementation(libs.firebase.auth)

    // When using the BoM, don't specify versions in Firebase dependencies
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)



    //Icons
    implementation(libs.androidx.material.icons.extended)

    //Navigation
    implementation(libs.androidx.navigation.compose)

    //Navigation Suite
    implementation(libs.androidx.compose.material3.adaptive.navigation)
    implementation(libs.androidx.compose.material3.adaptive.navigation.suite)


    //Dependencies
    implementation(libs.ktor.client.cio.jvm)
    implementation(libs.ktor.client.content.negotiation)

    implementation(libs.gson)
    implementation(libs.coil.compose)

    //Moshi
    implementation(libs.moshi.kotlin)

    //Coroutine
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.androidx.runtime)

    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)
    implementation (libs.logging.interceptor)

    //Room
    implementation (libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)


    //Dagger - Hilt
    implementation (libs.hilt.android)
    ksp (libs.hilt.android.compiler)
    ksp (libs.androidx.hilt.compiler)
    implementation (libs.androidx.hilt.navigation.compose)

    //Data Store
    implementation (libs.androidx.datastore)
    implementation (libs.kotlinx.collections.immutable)
    implementation (libs.kotlinx.serialization.json)
    implementation (libs.androidx.datastore.preferences)

    //Coil
    implementation (libs.coil.compose)

    //Date and Time Picker
    implementation(libs.datetime)
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    //Splash Screen
    implementation(libs.androidx.core.splashscreen)

    //Pager
    implementation(libs.accompanist.pager)






    //Local unit test
    testImplementation (libs.androidx.core)
    testImplementation (libs.junit)
    testImplementation (libs.androidx.core.testing)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation (libs.truth)
    testImplementation (libs.mockwebserver)
    testImplementation (libs.mockk)
    debugImplementation(libs.ui.test.manifest)

    //Instrumentation test
    androidTestImplementation (libs.hilt.android.testing)
    kspAndroidTest (libs.hilt.android.compiler)
    androidTestImplementation (libs.junit)
    androidTestImplementation (libs.kotlinx.coroutines.test)
    androidTestImplementation (libs.androidx.core.testing)
    androidTestImplementation (libs.truth)
    androidTestImplementation (libs.androidx.junit)
    androidTestImplementation (libs.core.ktx)
    androidTestImplementation (libs.mockwebserver)
    androidTestImplementation (libs.mockk.android)
    androidTestImplementation (libs.androidx.runner)
    androidTestImplementation (libs.androidx.espresso.core)
    androidTestImplementation (libs.androidx.ui.test.junit4)
    androidTestImplementation (libs.dexmaker.mockito)
    debugImplementation (libs.androidx.ui.tooling)
}


