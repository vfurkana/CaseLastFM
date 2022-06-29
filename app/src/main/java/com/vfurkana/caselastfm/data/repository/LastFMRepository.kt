package com.vfurkana.caselastfm.data.repository

import com.vfurkana.caselastfm.data.service.local.dao.AlbumsDao
import com.vfurkana.caselastfm.data.service.remote.api.LastFMAPI
import javax.inject.Inject


class LastFMRepository @Inject constructor(
    val remote: LastFMAPI,
    val local: AlbumsDao
) {

}
