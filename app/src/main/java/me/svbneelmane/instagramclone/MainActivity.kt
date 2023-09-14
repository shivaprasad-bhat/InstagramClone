package me.svbneelmane.instagramclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.svbneelmane.instagramclone.ui.screens.LoginScreen
import me.svbneelmane.instagramclone.ui.screens.SignupScreen
import me.svbneelmane.instagramclone.core.utils.NotificationMessage
import me.svbneelmane.instagramclone.ui.theme.InstagramCloneTheme
import me.svbneelmane.instagramclone.viewmodels.InstaViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstagramCloneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InstagramApp()
                }
            }
        }
    }
}

@Composable
fun InstagramApp() {
    val viewModel = hiltViewModel<InstaViewModel>()
    val navController = rememberNavController()

    NotificationMessage(viewModel = viewModel)

    NavHost(navController = navController, startDestination = DestinationScreen.Signup.route) {
        composable(DestinationScreen.Signup.route) {
            SignupScreen(navController = navController, viewModel = viewModel)
        }
        composable(DestinationScreen.Login.route) {
            LoginScreen(navController = navController, viewModel = viewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InstagramCloneTheme {
        InstagramApp()
    }
}

sealed class DestinationScreen(val route: String) {
    object Signup : DestinationScreen("signup")
    object Login : DestinationScreen("login")
}