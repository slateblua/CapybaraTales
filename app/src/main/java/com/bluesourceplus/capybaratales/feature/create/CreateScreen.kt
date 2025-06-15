package com.bluesourceplus.capybaratales.feature.create

import android.icu.text.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bluesourceplus.capybaratales.data.Mood
import com.bluesourceplus.capybaratales.ui.theme.CapybaraGreen
import com.bluesourceplus.capybaratales.ui.theme.CapybaraLightGreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.Date
import java.util.Locale

@Composable
fun CreateScreenRoute(mode: CreateGoalMode, back: () -> Unit) {
    val createViewModel: CreateViewModel = koinViewModel { parametersOf(mode) }
    CreateScreen(createViewModel, back)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(createViewModel: CreateViewModel, back: () -> Unit) {
    val state by createViewModel.state.collectAsStateWithLifecycle()

    var canBeCreated by remember { mutableStateOf(true) }

    // Collect side effects
    LaunchedEffect(Unit) {
        createViewModel.sideEffect.collect { effect ->
            when (effect) {
                CreateGoalEffect.MoodSaved -> {
                    // empty for now
                }
                CreateGoalEffect.NavigateUp -> {
                    back()
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background).padding()) {
        CenterAlignedTopAppBar(
            title = { Text(text = "Create")},
            navigationIcon = {
                IconButton(onClick = back) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = {
                    if ((state as State.Content).note.isEmpty()) {
                        canBeCreated = false
                    } else {
                        createViewModel.handleEvent(CreateGoalIntent.OnSaveClicked)
                    }
                }) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Daily Reflection",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = CapybaraGreen
            )

            Text(
                text = DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault()).format(Date()),
                fontSize = 16.sp,
                color = Color.Gray
            )

            Text(
                text = "How are you feeling?",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val moods = Mood.entries
                moods.forEachIndexed { index, mood ->
                    MoodButton(
                        mood = mood,
                        onClick = {
                            createViewModel.handleEvent(CreateGoalIntent.OnMoodChanged(Mood.entries[index]))
                        },
                        isSelected = (mood == (state as State.Content).mood),
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F8F0))
            ) {
                Text(
                    text = "💭 What made you smile today?",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp,
                )
            }

            OutlinedTextField(
                value = (state as State.Content).note,
                onValueChange = {
                    createViewModel.handleEvent(CreateGoalIntent.OnNoteChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = { Text("Write your thoughts here...") }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { createViewModel.handleEvent(CreateGoalIntent.OnSaveClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CapybaraGreen),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text("Save Entry", fontSize = 18.sp)
            }
        }
    }
}


@Composable
fun MoodButton(mood: Mood, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) CapybaraLightGreen else Color.Transparent
        ),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(mood.emoji, fontSize = 24.sp)
    }
}