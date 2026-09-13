plugins {
    alias(libs.plugins.composeuI.android.compose.library)
}

android {
    namespace = "com.hirezy.composeuI.feature.network"
}

dependencies {
    implementation(libs.navigation.compose)
    implementation(libs.bundles.retrofit)

    implementation(projects.core.ui.theme)
    implementation(projects.core.ui.components)
    implementation(projects.core.utils)
    implementation(projects.core.data.model)
}