package ru.normno.rutubedownloader.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
        modifier = Modifier
            .padding(8.dp),
    ){
        Text(data.visuals.message)
    }
}