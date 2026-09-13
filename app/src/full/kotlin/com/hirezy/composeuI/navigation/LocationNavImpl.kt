package com.hirezy.composeuI.navigation

import androidx.navigation.NavGraphBuilder
import com.hirezy.composeuI.feature.location.navigation.addLocationGraph

class LocationNavImpl : LocationNav {
    override fun addLocationGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.addLocationGraph()
    }
}
