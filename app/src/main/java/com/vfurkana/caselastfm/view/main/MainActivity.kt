package com.vfurkana.caselastfm.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.vfurkana.caselastfm.R
import com.vfurkana.caselastfm.data.service.remote.api.LastFMArtistAPI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var service: LastFMArtistAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            val johnnyMBID = service.searchArtist("Johnny Mercer").results.artistMatches.artists[0].mbid
            service.getTopAlbumsByArtist("Röyksopp")
            service.getTopAlbumsByMBID(johnnyMBID)
        }
    }
}