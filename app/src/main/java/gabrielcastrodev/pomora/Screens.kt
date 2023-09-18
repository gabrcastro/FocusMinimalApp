package gabrielcastrodev.pomora

sealed class Screens(val route: String) {
    object splash : Screens("splash_screen")
    object app : Screens("app_screen")
    object about : Screens("about_screen")
}
