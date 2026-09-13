package com.hirezy.composeuI.feature.media.screens.image

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.hirezy.composeuI.core.data.model.VisualMediaType
import com.hirezy.composeuI.core.ui.components.button.ButtonType
import com.hirezy.composeuI.core.ui.components.button.WeButton
import com.hirezy.composeuI.core.ui.components.mediapicker.rememberPickMediasLauncher
import com.hirezy.composeuI.core.ui.components.screen.WeScreen
import com.hirezy.composeuI.feature.media.imagecropper.rememberImageCropperLauncher

@Composable
fun ImageCropperScreen() {
    WeScreen(title = "ImageCropper", description = "图片裁剪") {
        var croppedImage by remember { mutableStateOf<ImageBitmap?>(null) }

        val context = LocalContext.current
        val launchImageCropper = rememberImageCropperLauncher { uri ->
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)?.asImageBitmap()?.let { imageBitmap ->
                    croppedImage = imageBitmap
                }
            }
        }
        val pickMedia = rememberPickMediasLauncher {
            val uri = it.first().uri
            launchImageCropper(uri)
        }

        croppedImage?.let {
            Image(bitmap = it, contentDescription = null)
            Spacer(modifier = Modifier.height(20.dp))
            WeButton(text = "清除图片", type = ButtonType.DANGER) {
                croppedImage = null
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        WeButton(text = "选择图片") {
            pickMedia(VisualMediaType.IMAGE, 1)
        }
    }
}