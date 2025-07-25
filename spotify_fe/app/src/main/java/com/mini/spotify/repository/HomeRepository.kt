package com.mini.spotify.repository

import com.mini.spotify.datamodel.Section
import com.mini.spotify.network.NetworkApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(private val networkApi: NetworkApi) {

    suspend fun getHomeSections(): List<Section> = withContext(Dispatchers.IO) {
        networkApi.getHomeFeed().execute().body() ?: listOf()
    }
}