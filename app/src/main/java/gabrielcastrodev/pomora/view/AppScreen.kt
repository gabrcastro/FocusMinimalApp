package gabrielcastrodev.pomora.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import gabrielcastrodev.pomora.R
import gabrielcastrodev.pomora.SettingsViewModel
import gabrielcastrodev.pomora.SettingsState
import gabrielcastrodev.pomora.TimerViewModel
import gabrielcastrodev.pomora.ui.theme.Blue
import gabrielcastrodev.pomora.ui.theme.BlueOpac
import gabrielcastrodev.pomora.ui.theme.Green
import gabrielcastrodev.pomora.ui.theme.GreenOpac
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavController, settingsViewModel: SettingsViewModel) {

    val openAlertDialog = remember { mutableStateOf(false) }
    val settingsState = settingsViewModel.state

    when {
        openAlertDialog.value -> {
            AlertDialogSettings(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    settingsViewModel.createSettings()
                    openAlertDialog.value = false
                },
                dialogTitle = "Configurações",
                timer = "25:00",
                pause = "5:00"
            )
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate("about_screen")
                        })
                    {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Settings",
                            Modifier.size(25.dp)
                        )
                    }
                    IconButton(onClick = { openAlertDialog.value = true }) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Settings",
                            Modifier.size(25.dp)
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(all = 24.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    )
                    {
                        Box(
                            modifier = Modifier.padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            TimerCount(
                                modifier = Modifier.size(200.dp),
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun TimerCount(
    viewModel: TimerViewModel = viewModel(),
    strokeWidth: Dp = 5.dp,
    modifier: Modifier = Modifier,
) {

    var size by remember {
        mutableStateOf(IntSize.Zero)
    }

    viewModel.apply {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.onSizeChanged { size = it }
        ) {
            Canvas(modifier = modifier) {
                drawArc(
                    color = if (isTimer.value) {
                        if (isPlaying.value) Green
                        else if (isPaused.value) GreenOpac else GreenOpac
                    } else {
                        if (isPlaying.value) Blue
                        else if (isPaused.value) BlueOpac else BlueOpac
                    },
                    startAngle = -215f,
                    sweepAngle = 250f,
                    useCenter = false,
                    size = Size(size.width.toFloat(), size.height.toFloat()),
                    style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }
        }

        Box {
            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(
                    text = viewModel.timerText.value,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isTimer.value) {
                        if (isPlaying.value) Green
                        else if (isPaused.value) GreenOpac else GreenOpac
                    } else {
                        if (isPlaying.value) Blue
                        else if (isPaused.value) BlueOpac else BlueOpac
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 50.dp, top = 80.dp),
                    textAlign = TextAlign.Center
                )

                ActionsTimer(
                    playPauseFunction = {
                        if (isPlaying.value) stopTimer() else startTimer()
                    },
                    restartFunction = {
                        resetTimer()
//                        value = 1f
                    },
                    icon = if (isPaused.value) R.drawable.outline_play_circle_outline_24
                    else if (isPlaying.value) R.drawable.outline_motion_photos_paused_24
                    else R.drawable.outline_play_circle_outline_24,

                    iconsColor = if (isTimer.value) {
                        if (isPlaying.value) Green
                        else if (isPaused.value) GreenOpac else GreenOpac
                    } else {
                        if (isPlaying.value) Blue
                        else if (isPaused.value) BlueOpac else BlueOpac
                    },
                )
            }
        }
    }
}

@Composable
fun ActionsTimer(
    playPauseFunction: () -> Unit,
    restartFunction: () -> Unit,
    @DrawableRes icon: Int,
    iconsColor: Color
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = restartFunction, modifier = Modifier.padding(end = 20.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.outline_replay_circle_filled_24),
                contentDescription = "Restart",
                Modifier.size(38.dp),
                tint = iconsColor
            )
        }
        IconButton(onClick = playPauseFunction) {
            Icon(
                painter = painterResource(icon),
                contentDescription = "Play",
                Modifier.size(40.dp),
                tint = iconsColor
            )
        }
    }
}

// Alert Dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogSettings(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    timer: String,
    pause: String,
) {

    var timerText by remember { mutableStateOf(timer) }
    var pauseText by remember { mutableStateOf(pause) }

    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        text = {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                OutlinedTextField(
                    label = {
                        Text(text = "Tempo de prática")
                    },
                    placeholder = {
                        Text(text = "ex: 25:00")
                    },
                    value = timerText,
                    onValueChange = { timerText = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.size(20.dp))
                OutlinedTextField(
                    label = {
                        Text(text = "Tempo de pausa")
                    },
                    placeholder = {
                        Text(text = "ex: 5:00")
                    },
                    value = pauseText,
                    onValueChange = { pauseText = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancelar")
            }
        }
    )
}