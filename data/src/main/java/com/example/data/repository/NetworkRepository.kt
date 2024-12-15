package com.example.data.repository

import android.content.res.Resources.NotFoundException
import com.example.domain.data.repository.CacheRepository
import com.example.data.network.Api
import com.example.domain.data.entity.ListElement
import com.example.domain.data.repository.ListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkRepository(
    private val api: Api,
    private val cacheRepository: CacheRepository
) : ListRepository {
    override suspend fun getList(): List<ListElement> = withContext(Dispatchers.IO) {
        //pi.getData().data.elements
        cacheRepository.getAndSave(
            force = true,
            key = "getList",
            remote = {
                api.getData().data.elements
            }
        )
    }
    override suspend fun getElement(id: Long): ListElement = withContext(Dispatchers.IO) {
        cacheRepository.getAndSave(
            force = true,
            key = "getList",
            remote = {
                api.getData().data.elements
            }
        ).find { it.id == id } ?: throw NotFoundException()
    }
}