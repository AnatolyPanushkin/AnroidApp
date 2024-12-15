package com.example.myapplication.details.vm

import com.example.domain.entity.ListElementEntity

sealed class DetailsState(val title: String) {
    data object Loading : DetailsState("Loading...")
    data class Error(
        val errorTitle: String,
        val message: String
    ) : DetailsState(errorTitle)

    data class Content(
        val element: ListElementEntity,
        val read: Boolean
    ) : DetailsState(element.title)
}