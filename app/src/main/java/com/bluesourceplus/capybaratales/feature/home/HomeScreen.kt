package com.bluesourceplus.capybaratales.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bluesourceplus.capybaratales.data.MoodModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel = koinViewModel(),
    onAddButton: () -> Unit,
    onMoodCardPressed: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        onAddButton = onAddButton,
        onMoodCardPressed = onMoodCardPressed,
        state = state,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddButton: () -> Unit,
    onMoodCardPressed: (Int) -> Unit,
    state: HomeScreenState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        CenterAlignedTopAppBar(
            title = { Text(text = "Capybara Tales") },
            actions = {
                IconButton(onClick = onAddButton, modifier = Modifier.padding(5.dp)) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Add new"
                    )
                }
            }
        )
        when (state) {
            is HomeScreenState.Content -> {
                LazyColumn(modifier = Modifier.padding(horizontal = 5.dp)) {
                    items(state.moods, key = { mood -> mood.id }) { mood ->
                        Spacer(modifier = Modifier.height(15.dp))
                        MoodCard(
                            modifier = Modifier.padding(5.dp),
                            mood = mood,
                        )
                    }
                }
            }

            HomeScreenState.Empty -> {
                HomeEmptyContent(onCreateButtonClicked = onAddButton)
            }
        }
    }
}

@Composable
fun HomeEmptyContent(
    modifier: Modifier = Modifier,
    onCreateButtonClicked: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "You don't have anything here yet!",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 22.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onCreateButtonClicked,
                content = {
                    Text(text = "Create one now!")
                },
                shape = RoundedCornerShape(10.dp)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoodCard(
    modifier: Modifier = Modifier,
    mood: MoodModel,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp)
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = mood.mood.emoji,
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}
