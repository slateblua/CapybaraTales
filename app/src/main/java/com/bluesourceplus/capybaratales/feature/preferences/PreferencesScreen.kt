package com.bluesourceplus.capybaratales.feature.preferences

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.bluesourceplus.capybaratales.feature.preferences.PreferencesViewModel
import com.bluesourceplus.capybaratales.ui.theme.CapybaraGreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun PreferencesScreenRoute(preferencesViewModel: PreferencesViewModel = koinViewModel(), back: () -> Unit) {
    PreferencesScreen(
        preferencesViewModel = preferencesViewModel,
        back = back,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("unused")
fun PreferencesScreen(preferencesViewModel: PreferencesViewModel, back: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)) {
        CenterAlignedTopAppBar(
            title = { Text(text = "Preferences")},
            navigationIcon = {
                IconButton(onClick = back) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(text = "Made by @slateblua")
        }
    }
}



@Composable
fun PreferencesItem(text: String, isEnabled: Boolean) {
    var enabled by remember { mutableStateOf(isEnabled) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, modifier = Modifier.weight(1f))
        Switch(
            checked = enabled,
            onCheckedChange = { enabled = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = CapybaraGreen
            )
        )
    }
}

