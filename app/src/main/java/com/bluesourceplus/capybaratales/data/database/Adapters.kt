package com.bluesourceplus.capybaratales.data.database

import com.bluesourceplus.capybaratales.data.Mood
import com.bluesourceplus.capybaratales.data.MoodModel

fun MoodModel.toEntry(): MoodEntry {
    return MoodEntry(
        id = this.id,
        mood = this.mood.name,
        timestamp = this.timestamp,
        note = this.note
    )
}

fun MoodEntry.toModel(): MoodModel {
    return MoodModel(
        id = this.id,
        mood = Mood.valueOf(this.mood),
        timestamp = this.timestamp,
        note = this.note
    )
}