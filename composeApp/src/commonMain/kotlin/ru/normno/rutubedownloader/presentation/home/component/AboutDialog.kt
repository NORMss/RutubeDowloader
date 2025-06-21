package ru.normno.rutubedownloader.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AboutDialog(
    onClose: () -> Unit,
) {
    Dialog(
        onDismissRequest = {
            onClose()
        },
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

        }
    }
}