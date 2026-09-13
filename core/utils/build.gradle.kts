plugins {
    alias(libs.plugins.composeuI.android.compose.library)
}

android {
    namespace = "com.hirezy.composeuI.core.utils"
}

dependencies {
    implementation(libs.accompanist.permissions)
    implementation(libs.pinyin)

    implementation(projects.core.data.model)
    implementation(projects.core.ui.theme)
}