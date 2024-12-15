package com.example.domain.mapper

interface Mapper<From, To> {
    fun map(from: From): To
}