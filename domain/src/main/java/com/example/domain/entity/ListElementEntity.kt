package com.example.domain.entity

import com.example.domain.data.entity.ListButton
data class ListElementEntity(
    val id: Long,
    val image: String?,
    val title: String,
    val subtitle: String?,
    val button: ListButton?,
    val like: Boolean
)