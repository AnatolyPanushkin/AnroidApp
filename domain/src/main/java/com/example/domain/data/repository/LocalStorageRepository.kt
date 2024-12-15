package com.example.domain.data.repository

interface LocalStorageRepository {
    fun markAsRead(id: Long)
    fun isMarkAsRead(id: Long): Boolean

    fun like(id: Long, like: Boolean)
    fun isLiked(id: Long): Boolean
}