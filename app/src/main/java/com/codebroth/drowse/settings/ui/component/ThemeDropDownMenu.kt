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

import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.codebroth.drowse.R
import com.codebroth.drowse.core.domain.model.ThemePreference
import com.codebroth.drowse.core.ui.theme.DrowseTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ThemeDropDownMenu(
    selectedTheme: ThemePreference,
    isDropDownExpanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
    onSelected: (ThemePreference) -> Unit,
    modifier: Modifier = Modifier,
) {
    ExposedDropdownMenuBox(
        expanded = isDropDownExpanded,
        onExpandedChange = onExpandedChanged,
        modifier = modifier.wrapContentWidth(),
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .widthIn(max = dimensionResource(R.dimen.theme_dropdown_box_max_width))
                .wrapContentWidth(),
            value = selectedTheme.name,
            onValueChange = { },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropDownExpanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = isDropDownExpanded,
            onDismissRequest = { onExpandedChanged(!isDropDownExpanded) }
        ) {
            ThemePreference.entries.forEach { theme ->
                DropdownMenuItem(
                    text = { Text(theme.name) },
                    onClick = {
                        onSelected(theme)
                        onExpandedChanged(!isDropDownExpanded)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun ThemeDropDownMenuPreview() {
    var isDropDownExpanded by rememberSaveable { mutableStateOf(false) }
    DrowseTheme {
        ThemeDropDownMenu(
            isDropDownExpanded = true,
            selectedTheme = ThemePreference.AUTO,
            onSelected = {},
            onExpandedChanged = { isDropDownExpanded = !isDropDownExpanded }
        )
    }
}

@Preview
@Composable
fun ThemeDropDownMenuDarkThemePreview() {
    var isDropDownExpanded by rememberSaveable { mutableStateOf(false) }
    DrowseTheme(darkTheme = true) {
        ThemeDropDownMenu(
            isDropDownExpanded = true,
            selectedTheme = ThemePreference.AUTO,
            onSelected = { },
            onExpandedChanged = { isDropDownExpanded = !isDropDownExpanded }
        )
    }
}