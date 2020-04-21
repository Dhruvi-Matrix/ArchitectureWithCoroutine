plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(29)
    buildToolsVersion= "29.0.2"
    dataBinding.isEnabled=true

    defaultConfig {
        applicationId ="com.example.architecturewithcoroutine"
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode =1
        versionName="1.0"
        testInstrumentationRunner= "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile(
                "proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }
}



dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.Android.supportAnnotations)
    implementation(Dependencies.Android.supportAppcompat)
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.Android.constraintLayout)
    implementation(Dependencies.Android.core)
    implementation(Dependencies.Android.legacySupport)

    implementation(Dependencies.Android.lifecycle)
    implementation(Dependencies.Android.lifecycleLiveData)
    implementation(Dependencies.Android.lifecycleViewModel)

    implementation(Dependencies.Android.retrofit)
    implementation(Dependencies.Android.retrofitConversion)

    implementation(Dependencies.Android.okHttp)
    implementation(Dependencies.Android.okHttpLoggingInterceptor)

    implementation(Dependencies.Android.room)
    implementation(Dependencies.Android.roomCoroutines)
    kapt(Dependencies.Android.roomAnnotation)

    implementation(Dependencies.Android.coroutineCore)
    implementation(Dependencies.Android.coroutineAndroid)

    implementation(Dependencies.Android.koinCore)
    implementation(Dependencies.Android.koinAndroid)
    implementation(Dependencies.Android.koinViewModel)
    implementation(Dependencies.Android.koinScope)
    implementation(Dependencies.Android.koinTest)

    implementation(Dependencies.Android.stetho)
    implementation(Dependencies.Android.stethoOkHttp)

    implementation(Dependencies.TestLibraries.espressoCore)
    implementation(Dependencies.TestLibraries.jUnit)
    implementation(Dependencies.TestLibraries.jUnitRunner)
    implementation(Dependencies.TestLibraries.jUnitTest)


    kapt(Dependencies.Android.databinding)
}
