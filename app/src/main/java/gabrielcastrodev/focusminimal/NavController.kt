package gabrielcastrodev.focusminimal

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import gabrielcastrodev.focusminimal.view.About
import gabrielcastrodev.focusminimal.view.App
import gabrielcastrodev.focusminimal.view.SplashScreen

@Composable
fun SetupNavController(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = Screens.splash.route) {

        composable(route = Screens.splash.route){
            SplashScreen(navController = navHostController)
        }
        composable(route = Screens.app.route) {
            App(navHostController)
        }
        composable(route = Screens.about.route) {
            About(navHostController)
        }
    }
}