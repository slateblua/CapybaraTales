package com.bluesourceplus.capybaratales.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
            .padding(horizontal = 16.dp, vertical = 8.dp), // More horizontal padding for a sleeker look
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // Slightly less elevation
        // Use a more subtle color, or even transparent if your screen background isn't white
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.large // Softer, larger rounded corners
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top // Align icon to the top of the text content
        ) {
            // Enhanced Mood Icon
            Box(
                modifier = Modifier
                    .size(56.dp) // Larger icon
                    .clip(CircleShape) // Circular background for the icon
                    .background(moodModel.mood.color.copy(alpha = 0.15f)) // Softer background based on mood color
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = moodModel.mood.icon,
                    contentDescription = "Mood: ${moodModel.mood.displayName}",
                    tint = moodModel.mood.color, // Mood color for the icon itself
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Mood Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = moodModel.mood.displayName,
                    style = MaterialTheme.typography.headlineSmall, // More prominent title
                    fontWeight = FontWeight.SemiBold,
                    color = moodModel.mood.color // Title color matches mood
                )

                Spacer(modifier = Modifier.height(6.dp))

                if (moodModel.note.isNotBlank()) {
                    Text(
                        text = moodModel.note,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        maxLines = 2, // Keep it concise
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                } else {
                    Spacer(modifier = Modifier.height(4.dp)) // Smaller space if no note
                }

                Text(
                    text = formatTimestamp(moodModel.timestamp),
                    style = MaterialTheme.typography.bodySmall, // Consistent small text style
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
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