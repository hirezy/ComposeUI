plugins {
    alias(libs.plugins.composeuI.android.compose.library)
    alias(libs.plugins.composeuI.android.room)
}

android {
    namespace = "com.hirezy.composeuI.feature.system"
}

dependencies {
    implementation(libs.navigation.compose)
    implementation(libs.accompanist.permissions)

    implementation(projects.core.ui.theme)
    implementation(projects.core.ui.components)
    implementation(projects.core.utils)
    implementation(projects.core.data.model)
}