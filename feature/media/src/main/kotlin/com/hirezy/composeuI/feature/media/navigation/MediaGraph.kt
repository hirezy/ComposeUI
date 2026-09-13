package com.hirezy.composeuI.feature.media.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hirezy.composeuI.feature.media.screens.audio.AudioPlayerScreen
import com.hirezy.composeuI.feature.media.screens.audio.AudioRecorderScreen
import com.hirezy.composeuI.feature.media.screens.camera.CameraScreen
import com.hirezy.composeuI.feature.media.screens.gallery.GalleryScreen
import com.hirezy.composeuI.feature.media.screens.image.ImageCropperScreen
import com.hirezy.composeuI.feature.media.screens.image.PanoramicImageScreen
import com.hirezy.composeuI.feature.media.screens.picker.MediaPickerScreen

fun NavGraphBuilder.addMediaGraph(navController: NavController) {
    composable("camera") {
        CameraScreen()
    }
    composable("media_picker") {
        MediaPickerScreen()
    }
    composable("audio_player") {
        AudioPlayerScreen()
    }
    composable("audio_recorder") {
        AudioRecorderScreen()
    }
    composable("gallery") {
        GalleryScreen(navController)
    }
    composable("image_cropper") {
        ImageCropperScreen()
    }
    composable("panoramic_image") {
        PanoramicImageScreen()
    }
}