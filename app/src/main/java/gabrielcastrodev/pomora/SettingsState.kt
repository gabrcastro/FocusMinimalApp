package gabrielcastrodev.pomora

import gabrielcastrodev.pomora.data.model.Settings
import java.util.UUID
import java.util.concurrent.TimeUnit

data class SettingsState (
    val settings: List<Settings> = mutableListOf<Settings>(
        Settings("001", "25", "5")
    ),
    val settingsTimer: String = "",
    val settingsPause: String = "",
    val settingsId: String? = null
)