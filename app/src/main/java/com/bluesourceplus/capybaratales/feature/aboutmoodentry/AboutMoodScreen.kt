package com.bluesourceplus.capybaratales.feature.aboutmoodentry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun AboutMoodRoute(
    aboutMoodViewModel: AboutMoodViewModel = koinViewModel(),
    moodId: Int,
    back: () -> Unit,
    onEditPressed: (Int) -> Unit
) {
    val state by aboutMoodViewModel.state.collectAsStateWithLifecycle()

    AboutMoodScreen(
        moodId = moodId,
        state = state,
        aboutMoodViewModel = aboutMoodViewModel,
        back = back,
        onUpdateMoodPressed = onEditPressed,
        onAboutMoodIntent = { aboutMoodViewModel.handleEvent(it) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("unused")
@Composable
fun AboutMoodScreen(
    moodId: Int,
    state: AboutMoodState,
    aboutMoodViewModel: AboutMoodViewModel,
    back: () -> Unit,
    onUpdateMoodPressed: (Int) -> Unit,
    onAboutMoodIntent: (AboutMoodIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        CenterAlignedTopAppBar(
            title = { Text(text = "About task") },
            navigationIcon = {
                IconButton(onClick = back) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
        )

        LaunchedEffect(Unit) {
            aboutMoodViewModel.handleEvent(AboutMoodIntent.LoadMood(moodId))
        }

        LaunchedEffect(Unit) {
            aboutMoodViewModel.sideEffect.collect { effect ->
                when (effect) {
                    AboutMoodEffect.MoodDeleted -> {
                        back()
                    }
                }
            }
        }

        when (state) {
            is AboutMoodState.Content -> {

            }
        }
    }
}