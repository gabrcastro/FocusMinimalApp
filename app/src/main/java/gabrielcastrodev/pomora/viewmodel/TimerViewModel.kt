package gabrielcastrodev.pomora.viewmodel


import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gabrielcastrodev.pomora.TimeFormat.timeFormat
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TimerViewModel(
): ViewModel() {

    val isTimer = mutableStateOf(true)
    val time = mutableStateOf("1")
    val isTimerFinish = mutableStateOf(false)
    val isPauseFinish = mutableStateOf(false)

    private var countDownTimer: CountDownTimer? = null
    val input = TimeUnit.MINUTES.toMillis(time.value.toLong())

    val initialTotalTimeInMillis = input
    var timeLeft = mutableStateOf(TimeUnit.MINUTES.toMillis(time.value.toLong()))
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
                isTimerFinish.value = true
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

    fun startPause() = viewModelScope.launch {
        time.value = "1"
        isPlaying.value = true
        isPaused.value = false
        isTimer.value = false
        countDownTimer = object : CountDownTimer(timeLeft.value, countDownInterval) {
            override fun onTick(currentTimeLeft: Long) {
                timerText.value = currentTimeLeft.timeFormat()
                timeLeft.value = currentTimeLeft
            }

            override fun onFinish() {
                timerText.value = initialTotalTimeInMillis.timeFormat()
                isPlaying.value = false
                isTimer.value = !isTimer.value
                time.value = "1"
            }
        }.start()
    }
}