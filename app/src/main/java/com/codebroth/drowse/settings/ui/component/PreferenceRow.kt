/*
 *
 *    Copyright 2025 Jayman Rana
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.codebroth.drowse.settings.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.codebroth.drowse.core.ui.theme.DrowseTheme

/**
 * A customizable row component that can include an icon, label, with optional description and trailing content.
 *
 * @param icon The icon to display on the left side of the row.
 * @param label The main text label for the row.
 * @param modifier Modifier to be applied to the row.
 * @param description Optional description text displayed below the label.
 * @param onClick Optional click action for the row.
 * @param trailingContent Optional composable content displayed at the end of the row (e.g., a switch or chevron).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceRow(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    onClick: (() -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        leadingContent = { Icon(icon, contentDescription = null) },
        headlineContent = { Text(label) },
        supportingContent = description?.let {
            {
                Text(
                    it,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        trailingContent = trailingContent?.let { trailingContent },
    )
}

@Preview
@Composable
fun DefaultPreferenceRowPreview() {
    DrowseTheme {
        PreferenceRow(
            icon = Icons.Default.Home,
            label = "Preference Label",
            onClick = {},
        )
    }
}

@Preview
@Composable
fun PreferenceRowWithDescriptionPreview() {
    DrowseTheme {
        PreferenceRow(
            icon = Icons.Default.Home,
            label = "Home",
            description = "This is a description.",
            onClick = {},
        )
    }
}

@Preview
@Composable
fun PreferenceRowWithChevronPreview() {
    DrowseTheme {
        PreferenceRow(
            icon = Icons.Default.Home,
            label = "Home",
            onClick = {},
            trailingContent = {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null
                )
            },
        )
    }
}

@Preview
@Composable
fun PreferenceRowWithSwitchPreview() {
    DrowseTheme {
        PreferenceRow(
            icon = Icons.Default.Home,
            label = "Home",
            onClick = {},
            trailingContent = {
                Switch(
                    checked = true,
                    onCheckedChange = null,
                )
            }
        )
    }
}

@Preview
@Composable
fun DefaultPreferenceRowDarkThemePreview() {
    DrowseTheme(darkTheme = true) {
        PreferenceRow(
            icon = Icons.Default.Home,
            label = "Preference Label",
            onClick = {},
        )
    }
}

@Preview
@Composable
fun PreferenceRowWithDescriptionDarkThemePreview() {
    DrowseTheme(darkTheme = true) {
        PreferenceRow(
            icon = Icons.Default.Home,
            label = "Home",
            description = "This is a description.",
            onClick = {},
        )
    }
}

@Preview
@Composable
fun PreferenceRowWithChevronDarkThemePreview() {
    DrowseTheme(darkTheme = true) {
        PreferenceRow(
            icon = Icons.Default.Home,
            label = "Home",
            onClick = {},
            trailingContent = {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null
                )
            },
        )
    }
}

@Preview
@Composable
fun PreferenceRowWithSwitchDarkThemePreview() {
    DrowseTheme(darkTheme = true) {
        PreferenceRow(
            icon = Icons.Default.Home,
            label = "Home",
            onClick = {},
            trailingContent = {
                Switch(
                    checked = true,
                    onCheckedChange = null,
                )
            }
        )
    }
}
