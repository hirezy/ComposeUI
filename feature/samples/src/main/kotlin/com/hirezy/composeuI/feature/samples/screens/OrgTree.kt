package com.hirezy.composeuI.feature.samples.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hirezy.composeuI.core.ui.components.screen.WeScreen
import com.hirezy.composeuI.feature.samples.components.WeOrgTree
import com.hirezy.composeuI.feature.samples.data.GovernmentDataProvider

@Composable
fun OrgTreeScreen() {
    WeScreen(title = "OrgTree", description = "组织架构树") {
        Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            WeOrgTree(GovernmentDataProvider.governmentMap, true)
        }
    }
}