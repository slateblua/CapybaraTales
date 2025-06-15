package com.bluesourceplus.capybaratales.data

import androidx.compose.runtime.Immutable

@Immutable
data class MoodModel(
    val id: Int = 0,
    val mood: Mood,
    val note: String,
    val timestamp: Long = System.currentTimeMillis(),
)

enum class Mood(val emoji: String) {
    SAD("😔"),
    EXCITED("🤩"),
    ANXIOUS("😟"),
    CALM("😌"),
    BORED("😒"),
    GRATEFUL("🙏");
}
