package gabrielcastrodev.pomora.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gabrielcastrodev.pomora.SettingsState
import gabrielcastrodev.pomora.data.model.Settings
import gabrielcastrodev.pomora.data.room.SettingsDao
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID

class SettingsViewModel(
    private val dao: SettingsDao
) : ViewModel() {

    var state by mutableStateOf(SettingsState())
    init {
        viewModelScope.launch {
            dao.getSettings().collectLatest {
                state = state.copy( settings = it)
            }
        }
    }

    fun changeSettingsTimer(timer: String) {
        state = state.copy( settingsTimer = timer )
    }

    fun changeSettingsPause(pause: String) {
        state = state.copy( settingsPause = pause )
    }

    fun createSettings(params: Settings?) {
        val settings = Settings(
            params?.id ?: UUID.randomUUID().toString(),
            state.settingsTimer,
            state.settingsPause
        )

        viewModelScope.launch {
            dao.insertSettings(settings)
        }
    }

//    fun editSettings(settings: Settings) {
//        state = state.copy(
//            settingsTimer = settings.timer,
//            settingsPause = settings.pause,
//            settingsId = settings.id
//        )
//    }
}