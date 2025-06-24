package ru.normno.rutubedownloader.presentation.common

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun AppSnackbar(data: SnackbarData) {
    Snackbar(
        action = {
            data.visuals.actionLabel?.let { actionLabel ->
                TextButton(
                    onClick = {
                        data.performAction()
                    }
                ) {
                    Text(actionLabel)
                }
            }
        },
    ){
        Text(data.visuals.message)
    }
}