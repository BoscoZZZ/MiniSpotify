package com.mini.spotify.repository

import com.mini.spotify.datamodel.Playlist
import com.mini.spotify.network.NetworkApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaylistRepository @Inject constructor(private val networkApi: NetworkApi) {

    suspend fun getPlaylist(id: Int): Playlist = withContext(Dispatchers.IO) {
        networkApi.getPlaylist(id).execute().body()!!
    }
}