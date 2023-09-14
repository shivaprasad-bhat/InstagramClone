package me.svbneelmane.instagramclone.core.utils

import androidx.navigation.NavController
import me.svbneelmane.instagramclone.core.nav.DestinationScreen


fun navigateTo(navController: NavController, destination: DestinationScreen) {
    navController.navigate(destination.route) {
        popUpTo(destination.route)
        launchSingleTop = true
    }

}