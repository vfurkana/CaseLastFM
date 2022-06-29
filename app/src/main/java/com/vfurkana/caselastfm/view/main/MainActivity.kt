package com.vfurkana.caselastfm.view.main

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.vfurkana.caselastfm.R
import com.vfurkana.caselastfm.data.service.local.dao.AlbumsDao
import com.vfurkana.caselastfm.data.service.local.model.*
import com.vfurkana.caselastfm.data.service.remote.api.LastFMAPI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var service: LastFMAPI
    @Inject lateinit var albumsDao: AlbumsDao

    var lastPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch(Dispatchers.IO) {
            val string = albumsDao.getAlbums().map { it.tracks?.map { it.name } }.toString()
            withContext(Dispatchers.Main) {
                findViewById<TextView>(R.id.tv).text = string
            }
        }

        findViewById<TextView>(R.id.tv).setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val artist = service.searchArtist("Kid Francescoli", page = lastPage).results.artistMatches.artists[0]
                albumsDao.insertArtist(
                    ArtistEntity(
                        BaseArtistEntity(artist.name, artist.mbid, artist.url),
                        artist.listeners,
                        artist.streamable
                    )
                )
                val mbid = artist.mbid
                val topAlbums = service.getTopAlbumsByMBID(mbid)
                val topAlbum = service.getAlbumInfo(topAlbums.topAlbums.album[0].name, artist.name)
                albumsDao.insertAlbum(
                    AlbumEntity(
                        BaseAlbumEntity(
                            topAlbum.album.name,
                            topAlbum.album.playcount.toLong(),
                            topAlbum.album.mbid,
                            topAlbum.album.url,
                            topAlbum.album.artist,
                            topAlbum.album.image.map {
                                ImageEntity(
                                    it.url,
                                    it.size?.name?.let { SizeEntity.valueOf(it) }
                                )
                            }
                        ),
                        topAlbum.album.tags.tag.map {
                            TagEntity(
                                it.url,
                                it.name
                            )
                        },
                        topAlbum.album.tracks.track.map {
                            TrackEntity(
                                it.streamable, it.duration, it.url, it.name, it.attr, it.artist
                            )
                        },
                        topAlbum.album.listeners
                    )
                )
                albumsDao.getAlbumsAndArtist().forEach {
                    Log.i("furk", "key: ${it.key.baseAlbumEntity.albumName}  vs. topAlbumName: ${topAlbum.album.name}| value: ${it.value}")
                }
            }
        }
    }
}