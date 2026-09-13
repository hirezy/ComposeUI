package com.hirezy.composeuI

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hirezy.composeuI.core.ui.theme.WeUITheme
import com.hirezy.composeuI.core.utils.clearAllCache
import com.hirezy.composeuI.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        clearAllCache()

        setContent {
            WeUITheme {
                AppNavHost()
            }
        }
    }
}
