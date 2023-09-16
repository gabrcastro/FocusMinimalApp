package gabrielcastrodev.focusminimal.view

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import gabrielcastrodev.focusminimal.R
import gabrielcastrodev.focusminimal.ui.theme.Green
import gabrielcastrodev.focusminimal.ui.theme.GreenOpac
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavController) {

    val openAlertDialog = remember { mutableStateOf(false) }

    when {
        openAlertDialog.value -> {
            AlertDialogSettings(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                    println("Confirmation registered") // Add logic here to handle confirmation.
                },
                dialogTitle = "Configurações",
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
                            /* doSomething() */
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
                Surface {
                    Body()
                }
            }
        }
    )
}

@Composable
fun Body() {
    Column(
        modifier = Modifier
            .padding(all = 24.dp)
            .fillMaxSize()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    )
    {
        Box(
            modifier = Modifier.padding(10.dp)
        ) {
            TimerCount(
                totalTime = 100L * 1000L,
                handleColor = Color.DarkGray,
                inactiveBarColor = Color.DarkGray,
                activeBarColor = Green,
                modifier = Modifier.size(200.dp)
            )
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
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(onClick = restartFunction) {
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


@Composable
fun TimerCount(
    totalTime: Long,
    handleColor: Color,
    inactiveBarColor: Color,
    activeBarColor: Color,
    modifier: Modifier = Modifier,
    initialValue: Float = 1f,
    strokeWidth: Dp = 5.dp
) {

    var size by remember {
        mutableStateOf(IntSize.Zero)
    }

    var value by remember {
        mutableStateOf(initialValue)
    }

    var currentTime by remember {
        mutableStateOf(totalTime)
    }

    var isTimerRunning by remember {
        mutableStateOf(false)
    }
    var isPaused by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (currentTime > 0 && isTimerRunning) {
            delay(100L)
            currentTime -= 100L
            value = currentTime / totalTime.toFloat()
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .onSizeChanged {
                size = it
            }
    ) {
        Canvas(modifier = modifier) {
            drawArc(
                color = inactiveBarColor,
                startAngle = -215f,
                sweepAngle = 250f,
                useCenter = false,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
            )

            drawArc(
                color = if (isTimerRunning && currentTime > 0) Green
                else if (isPaused) GreenOpac else Color.Gray,
                startAngle = -215f,
                sweepAngle = 250f * value,
                useCenter = false,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
            )

            val center = Offset(size.width / 2f, size.height / 2f)
            val beta = (250f * value + 145f) * (PI / 180f).toFloat()
            val r = size.width / 2f
            val a = cos(beta) * r
            val b = sin(beta) * r

            drawPoints(
                listOf(Offset(center.x + a, center.y + b)),
                pointMode = PointMode.Points,
                color = if (isTimerRunning && currentTime > 0) Green
                else if (isPaused) GreenOpac else Color.Gray,
                strokeWidth = (strokeWidth * 3f).toPx(),
                cap = StrokeCap.Round
            )
        }

        Text(
            text = (currentTime / 1000L).toString(),
            fontSize = 44.sp,
            fontWeight = FontWeight.Bold,
            color = if (isTimerRunning && currentTime > 0) Green
            else if (isPaused) GreenOpac else Color.Gray,
        )

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            ActionsTimer(
                playPauseFunction = {
                    if (currentTime <= 0L) {
                        currentTime = totalTime
                        isTimerRunning = true
                        isPaused = false
                    } else {
                        isTimerRunning = !isTimerRunning
                        isPaused = true
                    }
                    Log.i("test", currentTime.toString())
                },
                restartFunction = {
                    currentTime = totalTime
                    isTimerRunning = false
                    isPaused = false
                    value = 1f
                    Log.i("test", currentTime.toString())
                },
                icon = if (!isTimerRunning && currentTime > 0) R.drawable.outline_play_circle_outline_24
                else R.drawable.outline_motion_photos_paused_24,
                iconsColor = if (isTimerRunning && currentTime > 0) Green
                else if (isPaused) GreenOpac else Color.Gray,
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
) {
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
                       value = "",
                       onValueChange = {}
                   )
                   Spacer(modifier = Modifier.size(20.dp))
                   OutlinedTextField(
                       label = {
                           Text(text = "Tempo de pausa")
                       },
                       placeholder = {
                           Text(text = "ex: 5:00")
                       },
                       value = "",
                       onValueChange = {}
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