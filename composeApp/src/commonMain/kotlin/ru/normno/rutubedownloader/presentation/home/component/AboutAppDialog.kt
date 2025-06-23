package ru.normno.rutubedownloader.presentation.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.normno.rutubedownloader.util.platform.PlatformConfig
import rutubedownloader.composeapp.generated.resources.Res
import rutubedownloader.composeapp.generated.resources.github_mark
import rutubedownloader.composeapp.generated.resources.rutubedownloader_icon

@Composable
fun AboutAppDialog(
    onClose: () -> Unit,
) {
    Dialog(
        onDismissRequest = {
            onClose()
        },
    ) {
        Card {
            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(Res.drawable.rutubedownloader_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(bottom = 16.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f),
                ) {
                    Text(
                        text = "RuTube Downloader v${PlatformConfig.versionCode}",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    HorizontalDivider()
                    Spacer(
                        modifier = Modifier
                            .height(8.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.github_mark),
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                        Spacer(
                            modifier = Modifier
                                .width(8.dp)
                        )
                        Text(
                            buildAnnotatedString {
                                withLink(
                                    LinkAnnotation.Url(
                                        "https://github.com/NORMss/RutubeDowloader",
                                        TextLinkStyles(
                                            style = SpanStyle(
                                                color = MaterialTheme.colorScheme.primary,
                                            )
                                        )
                                    )
                                ) {
                                    append("Application on GitHub")
                                }
                            },
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .height(8.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                        Spacer(
                            modifier = Modifier
                                .width(8.dp)
                        )
                        Text(
                            buildAnnotatedString {
                                withLink(
                                    LinkAnnotation.Url(
                                        "https://github.com/NORMss",
                                        TextLinkStyles(
                                            style = SpanStyle(
                                                color = MaterialTheme.colorScheme.primary,
                                            )
                                        )
                                    )
                                ) {
                                    append("Author")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AboutDialogPreview() {
    AboutAppDialog(onClose = {})
}
