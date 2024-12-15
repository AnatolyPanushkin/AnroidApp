package com.example.domain.usecase

interface UseCase<D, R> {
    suspend fun execute(data: D): R
}