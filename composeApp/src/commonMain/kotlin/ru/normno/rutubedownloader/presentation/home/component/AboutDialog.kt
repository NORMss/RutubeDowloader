package ru.normno.rutubedownloader.presentation.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import rutubedownloader.composeapp.generated.resources.Res
import rutubedownloader.composeapp.generated.resources.rutubedownloader_icon

@Composable
fun AboutDialog(
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
                Column {
                    Text(
                        text = "RuTube Downloader v1.0"
                    )
                    Spacer(
                        modifier = Modifier
                            .height(8.dp)
                    )
                    Text(
                        buildAnnotatedString {
                            withLink(
                                LinkAnnotation.Url(
                                    "https://github.com/NORMss/RutubeDowloader",
                                    TextLinkStyles(
                                        style = SpanStyle(
                                            color = MaterialTheme.colorScheme.primary,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                    )
                                )
                            ) {
                                append("Application on GitHub")
                            }
                        },
                    )
                    Spacer(
                        modifier = Modifier
                            .height(8.dp)
                    )
                    Text(
                        buildAnnotatedString {
                            withLink(
                                LinkAnnotation.Url(
                                    "https://github.com/NORMss",
                                    TextLinkStyles(
                                        style = SpanStyle(
                                            color = MaterialTheme.colorScheme.primary,
                                            textDecoration = TextDecoration.Underline,
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

@Preview
@Composable
fun AboutDialogPreview() {
    AboutDialog(onClose = {})
}
