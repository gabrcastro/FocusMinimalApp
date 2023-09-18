package gabrielcastrodev.pomora


import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gabrielcastrodev.pomora.TimeFormat.timeFormat
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TimerViewModel : ViewModel() {

    val isTimer = mutableStateOf(false)

    private var countDownTimer: CountDownTimer? = null
    private val userInputHour = TimeUnit.HOURS.toMillis(0)
    private val userInputMinute = TimeUnit.MINUTES.toMillis(0)
    private val userInputSeconds = TimeUnit.SECONDS.toMillis(10)

    val initialTotalTimeInMillis = userInputHour + userInputMinute + userInputSeconds
    var timeLeft = mutableStateOf(initialTotalTimeInMillis)
    val countDownInterval = 1000L
    val timerText = mutableStateOf(timeLeft.value.timeFormat())
    val isPlaying = mutableStateOf(false)
    val isPaused = mutableStateOf(false)

    fun startTimer() = viewModelScope.launch {
        isPlaying.value = true
        isPaused.value = false
        countDownTimer = object : CountDownTimer(timeLeft.value, countDownInterval) {
            override fun onTick(currentTimeLeft: Long) {
                timerText.value = currentTimeLeft.timeFormat()
                timeLeft.value = currentTimeLeft
            }

            override fun onFinish() {
                timerText.value = initialTotalTimeInMillis.timeFormat()
                isPlaying.value = false
            }
        }.start()
    }

    fun stopTimer() = viewModelScope.launch {
        isPlaying.value = false
        isPaused.value = true
        countDownTimer?.cancel()
    }

    fun resetTimer() = viewModelScope.launch {
        isPlaying.value = false
        isPaused.value = false
        countDownTimer?.cancel()
        timerText.value = initialTotalTimeInMillis.timeFormat()
        timeLeft.value = initialTotalTimeInMillis
        isTimer.value = true
    }
}