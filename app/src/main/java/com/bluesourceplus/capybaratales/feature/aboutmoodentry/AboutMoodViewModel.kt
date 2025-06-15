package com.bluesourceplus.capybaratales.feature.aboutmoodentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluesourceplus.capybaratales.data.Mood
import com.bluesourceplus.capybaratales.feature.aboutmoodentry.usecases.DeleteMoodUseCase
import com.bluesourceplus.capybaratales.feature.aboutmoodentry.usecases.GetMoodByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed interface AboutMoodState {
    data class Content(
        val id: Int = 0,
        val note: String = "",
        val mood: Mood = Mood.GRATEFUL,
    ) : AboutMoodState
}

sealed class AboutMoodEffect {
    data object MoodDeleted : AboutMoodEffect()
}

sealed interface AboutMoodIntent {
    data class LoadMood(
        val goalId: Int,
    ) : AboutMoodIntent

    data class DeleteMood(
        val id: Int,
    ) : AboutMoodIntent
}


class AboutMoodViewModel : ViewModel(), KoinComponent {
    private val getMoodByIdUseCase: GetMoodByIdUseCase by inject()
    private val deleteMoodUseCase: DeleteMoodUseCase by inject()

    private val _state =
        MutableStateFlow<AboutMoodState>(
            AboutMoodState.Content()
        )
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<AboutMoodEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun handleEvent(event: AboutMoodIntent) {
        when (event) {
            is AboutMoodIntent.LoadMood -> loadGoal(event.goalId)
            is AboutMoodIntent.DeleteMood -> deleteGoal(goalId = event.id)
        }
    }

    private fun deleteGoal(goalId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val goal = getMoodByIdUseCase(goalId)
            deleteMoodUseCase(goal.first())
            _sideEffect.send(AboutMoodEffect.MoodDeleted)
        }
    }

    private fun loadGoal(goalId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val goal = getMoodByIdUseCase(goalId).first()
            _state.update {
                AboutMoodState.Content(
                    id = goalId,
                    note = goal.note,
                    mood = goal.mood,
                )
            }
        }
    }
}