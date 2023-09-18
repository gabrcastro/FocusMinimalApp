package gabrielcastrodev.pomora

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import gabrielcastrodev.pomora.view.About
import gabrielcastrodev.pomora.view.App
import gabrielcastrodev.pomora.view.SplashScreen

@Composable
fun SetupNavController(
    navHostController: NavHostController,
    settingsViewModel: SettingsViewModel
) {

    NavHost(
        navController = navHostController,
        startDestination = Screens.splash.route) {

        composable(route = Screens.splash.route){
            SplashScreen(navController = navHostController)
        }
        composable(route = Screens.app.route) {
            App(navHostController, settingsViewModel)
        }
        composable(route = Screens.about.route) {
            About(navHostController)
        }
    }
}