package com.hirezy.core.data.repository

import com.hirezy.composeuI.core.data.model.MediaItem
import com.hirezy.composeuI.core.data.model.MediaType


interface LocalMediaRepository {
    suspend fun loadMediaList(types: Array<MediaType>): List<MediaItem>
}