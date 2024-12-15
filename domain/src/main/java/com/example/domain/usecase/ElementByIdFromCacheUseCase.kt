package com.example.domain.usecase

import com.example.domain.data.entity.ListElement
import com.example.domain.data.repository.CacheRepository
import com.example.domain.entity.ListElementEntity
import com.example.domain.mapper.Mapper
class ElementByIdFromCacheUseCase(
    private val cacheRepository: CacheRepository,
    private val elementMapper: Mapper<ListElement, ListElementEntity>
) : UseCase<Long, ListElementEntity?> {
    override suspend fun execute(data: Long): ListElementEntity? {
        val cached = cacheRepository.getCache<List<ListElement>>("getList")
        return cached?.find {
            it.id == data
        }?.let {
            elementMapper.map(it)
        }
    }
}