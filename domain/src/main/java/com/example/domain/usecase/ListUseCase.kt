package com.example.domain.usecase

import com.example.domain.data.entity.ListElement
import com.example.domain.data.repository.ListRepository
import com.example.domain.entity.ListElementEntity
import com.example.domain.mapper.Mapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ListUseCase(
    private val repository: ListRepository,
    private val elementMapper: Mapper<ListElement, ListElementEntity>
) : UseCase<Unit, List<ListElementEntity>> {
    override suspend fun execute(data: Unit): List<ListElementEntity> =
        withContext(Dispatchers.Default) {
            delay(500)
            return@withContext repository.getList().map {
                elementMapper.map(it)
            }
        }
}