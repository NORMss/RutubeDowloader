package ru.normno.rutubedownloader.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun VideoCard(
    name: String,
    photo: String,
    onOpenVideo: () -> Unit,
    onShare: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        onClick = onOpenVideo,
    ) {
        Row(
            modifier = Modifier
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = photo,
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(64.dp),
                placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            )
            Spacer(
                modifier = Modifier
                    .width(8.dp),
            )
            Text(
                modifier = Modifier
                    .weight(1f),
                text = name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(
                modifier = Modifier
                    .width(8.dp),
            )
            IconButton(
                onClick = onShare,
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview
@Composable
fun VideoCardPreview() {
    VideoCard(
        name = "Sample Video Name",
        photo = "https://example.com/sample_photo.jpg",
        onOpenVideo = {},
        onShare = {},
    )
}
