plugins {
    alias(libs.plugins.composeuI.android.compose.application)
}

android {
    namespace = "com.hirezy.composeuI"
    defaultConfig {
        applicationId = "com.hirezy.composeuI"
        targetSdk = 36
        versionCode = 20260101
        versionName = "2026.01.01"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ndk {
            abiFilters.addAll(listOf("arm64-v8a"))
        }
    }

    buildTypes {
        /*debug {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }*/
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // 支持多版本构建
    buildFeatures {
        buildConfig = true
    }
    flavorDimensions.add("version")
    productFlavors {
        create("lite") {
            dimension = "version"
            applicationIdSuffix = ".lite"
            resValue("string", "app_name", "ComposeUI Lite")
        }
        create("full") {
            dimension = "version"
            resValue("string", "app_name", "ComposeUI")
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.navigation.compose)

    implementation(projects.core.ui.theme)
    implementation(projects.core.ui.components)
    implementation(projects.core.utils)
    implementation(projects.feature.basic)
    implementation(projects.feature.form)
    implementation(projects.feature.media)
    implementation(projects.feature.feedback)
    implementation(projects.feature.system)
    implementation(projects.feature.network)
    implementation(projects.feature.hardware)
    implementation(projects.feature.charts)
    implementation(projects.feature.qrcode)
    implementation(projects.feature.samples)

    // 仅全量版包含地图模块
    "fullImplementation"(projects.feature.location)

    // // 先运行 ./gradlew publishToMavenLocal
    // // 然后可以使用本地 Maven 发布的库
    // val composeuIVersion = "2026.01.01"
    // implementation("com.hirezy.composeuI:core-ui-theme:$composeuIVersion")
    // implementation("com.hirezy.composeuI:core-ui-components:$composeuIVersion")
    // implementation("com.hirezy.composeuI:core-utils:$composeuIVersion")
    // implementation("com.hirezy.composeuI:feature-basic:$composeuIVersion")
    // implementation("com.hirezy.composeuI:feature-form:$composeuIVersion")
    // implementation("com.hirezy.composeuI:feature-media:$composeuIVersion")
    // implementation("com.hirezy.composeuI:feature-feedback:$composeuIVersion")
    // implementation("com.hirezy.composeuI:feature-system:$composeuIVersion")
    // implementation("com.hirezy.composeuI:feature-network:$composeuIVersion")
    // implementation("com.hirezy.composeuI:feature-hardware:$composeuIVersion")
    // implementation("com.hirezy.composeuI:feature-charts:$composeuIVersion")
    // implementation("com.hirezy.composeuI:feature-qrcode:$composeuIVersion")
    // implementation("com.hirezy.composeuI:feature-samples:$composeuIVersion")

    // "fullImplementation"("com.hirezy.composeuI:feature-location:$composeuIVersion")
}