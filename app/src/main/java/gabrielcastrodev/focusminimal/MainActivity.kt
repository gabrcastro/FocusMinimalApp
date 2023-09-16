package gabrielcastrodev.focusminimal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import gabrielcastrodev.focusminimal.ui.theme.FocusMinimalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FocusMinimalTheme {
               SetupNavController(navHostController = rememberNavController())
            }
        }
    }
}
