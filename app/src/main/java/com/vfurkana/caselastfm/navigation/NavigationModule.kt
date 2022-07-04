package com.vfurkana.caselastfm.navigation

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.vfurkana.artistsearch.navigation.ArtistSearchNavigations
import com.vfurkana.caselastfm.R
import com.vfurkana.caselastfm.view.main.MainActivity
import com.vfurkana.savedalbums.navigation.SavedAlbumsNavigations
import com.vfurkana.topalbums.navigation.TopAlbumsNavigations
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped


@Module
@InstallIn(ActivityComponent::class)
class NavControllerModule {

    @Provides
    @ActivityScoped
    fun providesNavController(activity: FragmentActivity): NavController? {
        return (activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment)?.navController
    }
}

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationModule {
    @Binds
    abstract fun provideSavedAlbumsNavigation(navigationImpl: NavigationImpl): SavedAlbumsNavigations
    @Binds
    abstract fun provideArtistSearchAlbumsNavigation(navigationImpl: NavigationImpl): ArtistSearchNavigations
    @Binds
    abstract fun provideTopAlbumsAlbumsNavigation(navigationImpl: NavigationImpl): TopAlbumsNavigations
}