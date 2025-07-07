package ru.normno.rutubedownloader.presentation.home.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import rutubedownloader.composeapp.generated.resources.Res
import rutubedownloader.composeapp.generated.resources.cancel
import rutubedownloader.composeapp.generated.resources.delete
import rutubedownloader.composeapp.generated.resources.delete_video
import rutubedownloader.composeapp.generated.resources.sure_to_delete

@Composable
fun DeleteVideoDialog(
    deleteVideoFile: String?,
    onDeleteVideo: (String) -> Unit,
    showDeleteVideoDialog: (Boolean) -> Unit,
){
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
            )
        },
        title = {
            Text(
                text = stringResource(Res.string.delete_video),
            )
        },
        text = {
            Text(
                text = stringResource(Res.string.sure_to_delete),
            )
        },
        onDismissRequest = {
            showDeleteVideoDialog(false)
        },
        confirmButton = {
            Button(
                onClick = {
                    deleteVideoFile?.let {
                        onDeleteVideo(it)
                    }
                    showDeleteVideoDialog(false)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                )
            ) {
                Text(
                    text = stringResource(Res.string.delete),
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    showDeleteVideoDialog(false)
                },
            ) {
                Text(
                    text = stringResource(Res.string.cancel),
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
    )
}