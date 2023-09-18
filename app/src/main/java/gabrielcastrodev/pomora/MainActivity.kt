package gabrielcastrodev.pomora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import gabrielcastrodev.pomora.data.room.SettingsDatabase
import gabrielcastrodev.pomora.ui.theme.FocusMinimalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FocusMinimalTheme {
                val database = Room.databaseBuilder(
                    this,
                    SettingsDatabase::class.java,
                    "settings_db"
                ).build()
                val dao = database.dao
                val viewModel by viewModels<SettingsViewModel>(factoryProducer = {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return SettingsViewModel(dao) as T
                        }
                    }
                })
                SetupNavController(navHostController = rememberNavController(), viewModel)
            }
        }
    }
}
