

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
}

android {
    namespace = "com.zyc.loghelper"
    compileSdk = 34

    flavorDimensions += "buildtype"
    productFlavors {
        create("dev") {
            dimension = "buildtype"
            // 该 flavor 用于开发和调试，默认启用调试功能
        }
        create("prod") {
            dimension = "buildtype"
            // 该 flavor 用于生产环境，默认禁用调试功能
        }
    }
    defaultConfig {
        minSdk = 23
        targetSdk = 34

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
        debug {
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

    implementation(platform("androidx.compose:compose-bom:2025.08.01"))
    implementation("androidx.compose.material3:material3")
    // 可选 - 与 Activity 集成
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.3")
    implementation("androidx.compose.runtime:runtime-livedata")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation ("com.github.chuckerteam.chucker:library:3.5.0")
    implementation("com.elvishew:xlog:1.11.1")
    implementation("com.elvishew:xlog-libcat:1.0.0")
    implementation("com.tencent:mmkv:2.2.3")
}
group = "com.zyc"
// lib/build.gradle.kts

// loghelper/build.gradle.kts

publishing {
    publications {
        // 创建 devDebug 的发布配置
        create<MavenPublication>("devDebug") {
            groupId = "com.github.xiaochunzhi"
            artifactId = "LogHelper"
            version = "1.0.16-devDebug"
            afterEvaluate {
                from(components["devDebug"])
            }
        }
        // 创建 prodRelease 的发布配置
        create<MavenPublication>("prodRelease") {
            groupId = "com.github.xiaochunzhi"
            artifactId = "LogHelper"
            version = "1.0.16"
            afterEvaluate {
                from(components["prodRelease"])
            }
        }
    }
}