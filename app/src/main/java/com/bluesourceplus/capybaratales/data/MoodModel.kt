package com.bluesourceplus.capybaratales.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.MoodBad
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.filled.SentimentVerySatisfied as SentimentVeryDissatisfiedIcon

@Immutable
data class MoodModel(
    val id: Int = 0,
    val mood: Mood,
    val note: String,
    val timestamp: Long = System.currentTimeMillis(),
)

enum class Mood(val icon: ImageVector, val color: Color, val displayName: String) {
    SAD(Icons.Filled.SentimentVeryDissatisfied, Color(0xFFF44336), "Sad"), // Red
    TIRED(Icons.Filled.SentimentVeryDissatisfiedIcon, Color(0xFF757575), "Tired"), // Grey
    STRESSED(Icons.Filled.SentimentDissatisfied, Color(0xFFFFA000), "Stressed"), // Orange
    ANGRY(Icons.Filled.MoodBad, Color(0xFFE53935), "Angry"), // Darker Red
    NEUTRAL(Icons.Filled.SentimentSatisfied, Color(0xFFFFC107), "Okay"), // Amber
    HAPPY(Icons.Filled.Mood, Color(0xFF4CAF50), "Happy"), // Green
    EXCITED(Icons.Filled.Mood, Color(0xFFFFEB3B), "Excited"), // Yellow
}
