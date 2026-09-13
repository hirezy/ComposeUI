plugins {
    alias(libs.plugins.composeuI.android.compose.library)
}

android {
    namespace = "com.hirezy.composeuI.feature.feedback"
}

dependencies {
    implementation(libs.navigation.compose)

    implementation(projects.core.ui.theme)
    implementation(projects.core.ui.components)
    implementation(projects.core.data.model)
    implementation(projects.core.utils)
}