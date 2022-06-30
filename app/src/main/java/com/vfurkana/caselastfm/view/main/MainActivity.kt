package com.vfurkana.caselastfm.view.main

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.vfurkana.caselastfm.R
import com.vfurkana.caselastfm.data.repository.LastFMRepository
import com.vfurkana.caselastfm.data.service.local.dao.AlbumsDao
import com.vfurkana.caselastfm.data.service.remote.api.LastFMAPI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var repository: LastFMRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        refreshSavedAlbums()

        findViewById<TextView>(R.id.tv).setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                repository.searchArtist("Arctic Monkeys").collect {
                    val firstArtist = it[0]
                    repository.getArtistTopAlbums(firstArtist.name).collect {
                        val firstAlbum = it[0]
                        val firstAlbumDetail = repository.getArtistAlbum(firstArtist.name, firstAlbum.name)
                        repository.saveAlbum(firstAlbumDetail)
                        refreshSavedAlbums()
                    }
                }
            }
        }
    }

    private fun refreshSavedAlbums() {
        lifecycleScope.launch(Dispatchers.IO) {
            val string = repository.getSavedAlbums().map { "${it.tracks.track.map { it.name }} \n" }.toString()
            withContext(Dispatchers.Main) {
                findViewById<TextView>(R.id.tv).text = string
            }
        }
    }
}