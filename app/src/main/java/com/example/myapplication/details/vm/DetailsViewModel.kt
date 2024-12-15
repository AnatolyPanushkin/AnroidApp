package com.example.myapplication.details.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.domain.data.repository.LocalStorageRepository
import com.example.domain.entity.ListElementEntity
import com.example.domain.usecase.ElementByIdUseCase
import com.example.myapplication.details.DetailsScreenRoute
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val useCase: ElementByIdUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val storage: LocalStorageRepository
) : ViewModel() {
    private val _state = MutableStateFlow<DetailsState>(DetailsState.Loading)
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            val title = when (val currentState = _state.value) {
                is DetailsState.Content -> {
                    currentState.title
                }

                is DetailsState.Error -> {
                    currentState.title
                }

                DetailsState.Loading -> {
                    "Ошибка"
                }
            }
            _state.emit(DetailsState.Error(title, throwable.message ?: "Ошибочка"))
        }
    }

    val state: StateFlow<DetailsState>
        get() = _state

    init {
        loadContent()
    }

    fun markAsRead() {
        val route = savedStateHandle.toRoute<DetailsScreenRoute>()
        storage.markAsRead(route.id)
        loadContent()
    }

    private fun loadContent() {
        viewModelScope.launch(context = exceptionHandler) {
            val route = savedStateHandle.toRoute<DetailsScreenRoute>()
            val result = useCase.execute(route.id)
            _state.emit(DetailsState.Content(result, storage.isMarkAsRead(route.id)))
        }
    }

    fun like(elementEntity: ListElementEntity, like: Boolean) {
        storage.like(elementEntity.id, like)
    }
}