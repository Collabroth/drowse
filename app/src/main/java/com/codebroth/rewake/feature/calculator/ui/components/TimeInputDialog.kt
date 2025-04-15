package com.codebroth.rewake.feature.calculator.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.codebroth.rewake.R

@Composable
fun TimeInputDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(stringResource(R.string.action_dismiss))
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(stringResource(R.string.action_confirm))
            }
        },
        text = { content() }
    )
}

@Preview
@Composable
fun TimeInputDialogPreview() {
    TimeInputDialog(
        onDismiss = {},
        onConfirm = {},
        content = {}
    )
}