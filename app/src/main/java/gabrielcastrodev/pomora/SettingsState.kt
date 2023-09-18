package gabrielcastrodev.pomora

import gabrielcastrodev.pomora.data.model.Settings
import java.util.UUID
import java.util.concurrent.TimeUnit

data class SettingsState (
    val settings: List<Settings> = mutableListOf<Settings>(
        Settings(UUID.randomUUID().toString(), TimeUnit.MINUTES.toMillis(25).toString(), TimeUnit.MINUTES.toMillis(5).toString())
    ),
    val settingsTimer: String = "",
    val settingsPause: String = "",
    val settingsId: String? = null
)