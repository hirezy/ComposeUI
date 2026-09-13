plugins {
    alias(libs.plugins.composeuI.android.compose.library)
}

android {
    namespace = "com.hirezy.core.data.repository"
}

dependencies {
    implementation(projects.core.data.model)
}