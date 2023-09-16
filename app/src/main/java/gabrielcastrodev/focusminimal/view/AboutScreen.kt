package gabrielcastrodev.focusminimal.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gabrielcastrodev.focusminimal.R
import gabrielcastrodev.focusminimal.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun About(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxHeight(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.about),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Outlined.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Box(modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()) {
                BodyAbout()
            }
        }
    )

}

@Composable
fun BodyAbout() {
    Column(
        modifier = Modifier
            .padding(all = 24.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_focus_minimal),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 30.dp, top = 10.dp)
            )
            Text(
                text = stringResource(R.string.version),
                modifier = Modifier
                    .padding(bottom = 40.dp)
            )
        }
        CreatedBy()
    }
}

@Composable
fun CreatedBy() {

    val localUriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.find_me_here))
        Row(
            modifier = Modifier
                .padding(
                    vertical = 30.dp
                )
                .fillMaxWidth(0.6f),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            IconButton(onClick = {
                localUriHandler.openUri("https://www.youtube.com/@gabrielcastrodev")
            }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_youtube),
                    contentDescription = "YouTube Channel",
                    colorFilter = ColorFilter.tint(White),
                    modifier = Modifier.size(30.dp)
                )
            }
            IconButton(onClick = {
                localUriHandler.openUri("https://github.com/gabrcastro")
            }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_github),
                    contentDescription = "GitHub",
                    colorFilter = ColorFilter.tint(White),
                    modifier = Modifier.size(29.dp)
                )
            }
            IconButton(onClick = {
                localUriHandler.openUri("https://www.instagram.com/gabrielcastrodev/")
            }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_instagram),
                    contentDescription = "Instagram",
                    colorFilter = ColorFilter.tint(White),
                    modifier = Modifier.size(29.dp)
                )
            }
            IconButton(onClick = {
                localUriHandler.openUri("https://www.linkedin.com/in/gabrielsouzacastro/")
            }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_linkedin),
                    contentDescription = "Linkedin",
                    colorFilter = ColorFilter.tint(White),
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = stringResource(R.string.by_gabriel_castro_dev))
    }
}