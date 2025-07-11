package com.bluesourceplus.capybaratales.feature.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluesourceplus.capybaratales.feature.create.usecases.AddMoodUseCase
import com.bluesourceplus.capybaratales.feature.create.usecases.UpdateMoodUseCase
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

sealed class CreateMoodMode {
    data object Create : CreateMoodMode()
    data class Edit(val moodId: Int) : CreateMoodMode()
}

sealed interface CreateMoodIntent {
    data class OnMoodChanged(
        val mood: Mood,
    ) : CreateMoodIntent

    data class OnNoteChanged(
        val description: String,
    ) : CreateMoodIntent

    data object OnSaveClicked : CreateMoodIntent
}

sealed interface State {
    data class Content(
        val id: Int = 0,
        val mood: Mood = Mood.SAD,
        val note: String = "",
    ) : State
}

sealed class CreateMoodEffect {
    data object MoodSaved : CreateMoodEffect()
    data object NavigateUp : CreateMoodEffect()
}

class CreateViewModel(private val mode: CreateMoodMode) : ViewModel(), KoinComponent {
    private val addMoodUseCase: AddMoodUseCase by inject()
    private val getMoodByIdUseCase: GetMoodByIdUseCase by inject()
    private val updateMoodUseCase: UpdateMoodUseCase by inject()

    init {
        if (mode is CreateMoodMode.Edit) {
            loadMood(mode.moodId)
        }
    }

    private val _sideEffect = Channel<CreateMoodEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _state =
        MutableStateFlow<State>(
            State.Content(),
        )
    val state = _state.asStateFlow()

    fun handleEvent(event: CreateMoodIntent) {
        when (event) {
            is CreateMoodIntent.OnNoteChanged -> setDescription(event.description)
            is CreateMoodIntent.OnSaveClicked -> addOrUpdateMood()
            is CreateMoodIntent.OnMoodChanged -> setMood(event.mood)
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
            val mood = getMoodByIdUseCase(moodId).first()
            _state.update {
                State.Content(
                    id = mood.id,
                    mood = mood.mood,
                    note = mood.note,
                )
            }
        }
    }

    private fun addOrUpdateMood() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _state.value as State.Content
            val mood =
                MoodModel(
                    id = state.id,
                    note = state.note,
                    mood = state.mood,
                )

            if (mode is CreateMoodMode.Edit) {
                updateMoodUseCase(mood)
            } else {
                addMoodUseCase(mood)
            }

            _sideEffect.send(CreateMoodEffect.MoodSaved)
            _sideEffect.send(CreateMoodEffect.NavigateUp)
        }
    }
}
