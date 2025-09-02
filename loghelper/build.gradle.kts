

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
}

android {
    namespace = "com.zyc.loghelper"
    compileSdk = 34

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
publishing {
    // 这个配置对于 JitPack 不是必需的，但遵循标准 Maven 发布流程是个好习惯
    publications {
        create<MavenPublication>("release") {
            groupId = "com.zyc"
            artifactId = "loghelper" // 你的库名
            version = "1.0.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}