package ru.normno.rutubedownloader.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    state: HomeState,
    setVideoUrl: (String) -> Unit,
    onGetVideo: () -> Unit,
) {
    var isSelectedResolution by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            TextField(
                value = state.videoUrlWithId,
                onValueChange = setVideoUrl,
                label = {
                    Text(
                        text = "Video URL",
                    )
                }
            )
        }
        Button(
            onClick = onGetVideo,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        ) {
            Text(
                text = "Get Video"
            )
        }
        Text(
            text = state.videoUrlM3U8?.title ?: "",
        )
        state.videoQuality?.let { videoQuality ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isSelectedResolution = true
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = videoQuality.resolution,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(
                        modifier = Modifier
                            .width(4.dp),
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier
                            .rotate(if (isSelectStegoMethod) 180f else 0f),
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
                DropdownMenu(
                    expanded = isSelectStegoMethod,
                    onDismissRequest = { isSelectStegoMethod = false },
                ) {
                    listOf(
                        StegoImageMethod.KJB,
                        StegoImageMethod.LSBMR,
                        StegoImageMethod.INMI,
                        StegoImageMethod.IMNP,
                    ).forEach { method ->
                        DropdownMenuItem(
                            onClick = {
                                onSelectStegoMethod(method)
                                isSelectStegoMethod = false
                            },
                            text = {
                                Text(text = method::class.simpleName ?: "Unknown")
                            }
                        )
                    }
                }
            }
        }
    }
}