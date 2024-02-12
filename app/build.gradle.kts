plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp").version("1.9.22-1.0.17")

}

android {
    namespace = "com.pottymotty.gitreposearch"
    compileSdk = 34
    buildFeatures.buildConfig = true


    defaultConfig {
        applicationId = "com.pottymotty.gitreposearch"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        this.buildConfigField("String","BASE_API_URL","\"https://api.github.com/\"")
        this.buildConfigField("String","GITHUB_API_VERSION","\"2022-11-28\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
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
    implementation(libs.bundles.compose)
    implementation(libs.bundles.network)
    implementation(libs.bundles.moshi)
    implementation(libs.bundles.koin)
    implementation(libs.timber)
    implementation(libs.room)
    implementation(libs.room.paging)
    implementation(libs.room.extensions)
    implementation(libs.bundles.voyager)
    implementation(libs.bundles.pager)
    implementation(libs.coil.compose)
    implementation(libs.coil)

    ksp(libs.moshi.codegen)
    ksp(libs.room.compiler)
}