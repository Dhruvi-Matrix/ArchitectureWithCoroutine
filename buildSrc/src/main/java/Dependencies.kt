
object Dependencies{
    object Versions {
        const val lifecycleVersion="2.2.0"
        const val lifecycleViewModelVersion="2.3.0-alpha01"

        const val constraintLayoutVersion = "2.0.0-alpha2"
        const val roomVersion = "2.2.2"

        const val legacySupportVersion="1.0.0"
        const val coreVersion="1.1.0"
        const val kotlinVersion = "1.3.50"
        const val androidGradlePluginVersion = "3.5.2"
        const val androidSupportVersion = "1.0.0-rc01"

        const val junitVersion = "4.12"
        const val junitRunnerVersion = androidSupportVersion
        const val junitTestVersion = "1.1.1"
        const val espressoCoreVersion = "3.1.0-alpha1"

        const val retrofitVersion="2.6.0"
        const val retrofitConverterVersion="2.6.1"

        const val okHttpVersion="3.12.2"

        const val coroutinesVersion="1.3.2"
        const val koinVersion="2.1.0"
        const val stethoVersion="1.5.1"

        const val databindingVersion="2.3.1"


    }

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlinVersion}"

    object BuildPlugins {
        const val androidPlugin = "com.android.tools.build:gradle:${Versions.androidGradlePluginVersion}"
        const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    }

    object Android {
        const val supportAnnotations = "androidx.annotation:annotation:${Versions.androidSupportVersion}"
        const val supportAppcompat = "androidx.appcompat:appcompat:${Versions.androidSupportVersion}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
        const val core="androidx.core:core-ktx:${Versions.coreVersion}"
        const val legacySupport="androidx.legacy:legacy-support-v4:${Versions.legacySupportVersion}"

        const val lifecycle="androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleVersion}"
        const val lifecycleLiveData="androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleViewModelVersion}"
        const val lifecycleViewModel="androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewModelVersion}"

        const val room="androidx.room:room-ktx:${Versions.roomVersion}"
        const val roomAnnotation="androidx.room:room-compiler:${Versions.roomVersion}"

        const val databinding="com.android.databinding:compiler:${Versions.databindingVersion}"

        const val retrofit="com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
        const val retrofitConversion="com.squareup.retrofit2:converter-gson:${Versions.retrofitConverterVersion}"

        const val okHttp="com.squareup.okhttp3:okhttp:${Versions.okHttpVersion}"
        const val okHttpLoggingInterceptor="com.squareup.okhttp3:logging-interceptor:${Versions.okHttpVersion}"

        const val coroutineAndroid="org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
        const val coroutineCore="org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"

        const val koinCore="org.koin:koin-core:${Versions.koinVersion}"
        const val koinAndroid="org.koin:koin-android:${Versions.koinVersion}"
        const val koinViewModel="org.koin:koin-androidx-viewmodel:${Versions.koinVersion}"
        const val koinScope="org.koin:koin-androidx-scope:${Versions.koinVersion}"
        const val koinTest="org.koin:koin-test:${Versions.koinVersion}"

        const val stetho="com.facebook.stetho:stetho:${Versions.stethoVersion}"
        const val stethoOkHttp="com.facebook.stetho:stetho-okhttp3:${Versions.stethoVersion}"
    }

    object TestLibraries {
        const val jUnit = "junit:junit:${Versions.junitVersion}"
        const val jUnitRunner = "androidx.test:runner:${Versions.junitRunnerVersion}"
        const val jUnitTest = "androidx.test.ext:junit:${Versions.junitTestVersion}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCoreVersion}"
    }
}