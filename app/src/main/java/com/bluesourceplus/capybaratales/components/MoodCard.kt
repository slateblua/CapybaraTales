package com.bluesourceplus.capybaratales.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bluesourceplus.capybaratales.data.Mood
import com.bluesourceplus.capybaratales.data.MoodModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// --- Composable for the Mood Card ---
@Composable
fun MoodCard(moodModel: MoodModel, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Mood Icon
            Icon(
                imageVector = moodModel.mood.icon,
                contentDescription = "Mood: ${moodModel.mood.displayName}",
                tint = moodModel.mood.color,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Mood Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = moodModel.mood.displayName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (moodModel.note.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = moodModel.note,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                        maxLines = 3 // Limit note length for display
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = formatTimestamp(moodModel.timestamp),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }
        }
    }
}

// --- Helper function to format timestamp ---
fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}


// --- Preview ---
@Preview(showBackground = true)
@Composable
fun HappyMoodCardPreview() {
    MaterialTheme { // Ensure a MaterialTheme is applied for previews
        MoodCard(
            moodModel = MoodModel(
                mood = Mood.HAPPY,
                note = "Had a wonderful day coding and learned a lot about Jetpack Compose!",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 2 // 2 hours ago
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NeutralMoodCardPreview() {
    MaterialTheme {
        MoodCard(
            moodModel = MoodModel(
                mood = Mood.NEUTRAL,
                note = "Just a regular day. Nothing special to report.",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 3 // 3 days ago
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SadMoodCardPreview_NoNote() {
    MaterialTheme {
        MoodCard(
            moodModel = MoodModel(
                mood = Mood.SAD,
                note = "", // Empty note
                timestamp = System.currentTimeMillis() - 1000 * 60 * 30 // 30 minutes ago
            )
        )
    }
}