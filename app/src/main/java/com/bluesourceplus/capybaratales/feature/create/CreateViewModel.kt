package com.bluesourceplus.capybaratales.feature.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluesourceplus.bluedays.feature.create.usecases.AddGoalUseCase
import com.bluesourceplus.bluedays.feature.create.usecases.UpdateGoalUseCase
import com.bluesourceplus.capybaratales.data.Mood
import com.bluesourceplus.capybaratales.data.MoodModel
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

sealed class CreateGoalMode {
    data object Create : CreateGoalMode()
    data class Edit(val goalId: Int) : CreateGoalMode()
}

sealed interface CreateGoalIntent {
    data class OnMoodChanged(
        val mood: Mood,
    ) : CreateGoalIntent

    data class OnNoteChanged(
        val description: String,
    ) : CreateGoalIntent

    data object OnSaveClicked : CreateGoalIntent
}

sealed interface State {
    data class Content(
        val id: Int = 0,
        val mood: Mood = Mood.CALM,
        val note: String = "",
    ) : State
}

sealed class CreateGoalEffect {
    data object MoodSaved : CreateGoalEffect()
    data object NavigateUp : CreateGoalEffect()
}

class CreateViewModel(private val mode: CreateGoalMode) : ViewModel(), KoinComponent {
    private val addGoalUseCase: AddGoalUseCase by inject()
    private val getMoodByIdUseCase: GetMoodByIdUseCase by inject()
    private val updateGoalUseCase: UpdateGoalUseCase by inject()

    init {
        if (mode is CreateGoalMode.Edit) {
            loadMood(mode.goalId)
        }
    }

    private val _sideEffect = Channel<CreateGoalEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _state =
        MutableStateFlow<State>(
            State.Content(),
        )
    val state = _state.asStateFlow()

    fun handleEvent(event: CreateGoalIntent) {
        when (event) {
            is CreateGoalIntent.OnNoteChanged -> setDescription(event.description)
            is CreateGoalIntent.OnSaveClicked -> addOrUpdateMood()
            is CreateGoalIntent.OnMoodChanged -> setMood(event.mood)
        }
    }

    private fun setMood(mood: Mood) {
        _state.update {
            (it as State.Content).copy(mood = mood)
        }
    }

    private fun setDescription(note: String) {
        _state.update {
            (it as State.Content).copy(note = note)
        }
    }

    private fun loadMood(moodId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val goal = getMoodByIdUseCase(moodId).first()
            _state.update {
                State.Content(
                    id = goal.id,
                    mood = goal.mood,
                    note = goal.note,
                )
            }
        }
    }

    private fun addOrUpdateMood() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _state.value as State.Content
            val goal =
                MoodModel(
                    id = state.id,
                    note = state.note,
                    mood = state.mood,
                )

            if (mode is CreateGoalMode.Edit) {
                updateGoalUseCase(goal)
            } else {
                addGoalUseCase(goal)
            }

            _sideEffect.send(CreateGoalEffect.MoodSaved)
            _sideEffect.send(CreateGoalEffect.NavigateUp)
        }
    }
}
