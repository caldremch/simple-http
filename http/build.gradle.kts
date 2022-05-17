plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

//ext {
//    myLibraryVersion = "1.0.0"
//    myBintrayName = "http"
//    myArtifactId = myBintrayName
//    myLibraryName = "http for android"
//    myLibraryDescription = "http for android"
//}

android {
    compileSdk = 32
    defaultConfig {
        minSdk = 21
        targetSdk = 32
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    signingConfigs {
        create("config") {
            keyAlias = "laboratory"
            keyPassword = "123456"
            storeFile = project.rootProject.projectDir.resolve("key.jks")
            storePassword = "123456"
            enableV1Signing = true
            enableV2Signing = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("config")

        }

        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("config")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    dataBinding {
        isEnabled = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


val versions_retrofit = "2.9.0"
val versions_okhttp3 = "4.8.1"

dependencies {
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlin.ktx)
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.junit.ext)
    androidTestImplementation(libs.androidx.espresso.core)

    //网络请求依赖
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.squareup.retrofit2:converter-gson:$versions_retrofit")
    api("com.squareup.retrofit2:adapter-rxjava3:$versions_retrofit")
    api("androidx.lifecycle:lifecycle-common-java8:2.2.0")
    api("io.reactivex.rxjava3:rxandroid:3.0.0")
    api("com.uber.autodispose2:autodispose-androidx-lifecycle:2.0.0")
    api("com.squareup.okhttp3:okhttp:4.8.1")
    api("com.squareup.okhttp3:logging-interceptor:$versions_okhttp3")
    api("io.reactivex.rxjava3:rxjava:3.0.6")
}

//apply from: "../upload.gradle"

