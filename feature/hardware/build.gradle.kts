plugins {
    alias(libs.plugins.composeuI.android.compose.library)
}

android {
    namespace = "com.hirezy.composeuI.feature.hardware"
}

dependencies {
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.navigation.compose)
    implementation(libs.accompanist.permissions)
    implementation(libs.biometric)

    implementation(projects.core.ui.theme)
    implementation(projects.core.ui.components)
    implementation(projects.core.utils)
}