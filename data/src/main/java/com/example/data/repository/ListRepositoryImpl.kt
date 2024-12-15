package com.example.data.repository

import com.example.domain.data.entity.ListButton
import com.example.domain.data.entity.ListElement
import com.example.domain.data.repository.ListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ListRepositoryImpl : ListRepository {
    override suspend fun getList(): List<ListElement> = withContext(Dispatchers.IO) {
        delay(5_000)
        return@withContext listOf(
            ListElement(
                id = 0,
                image = "https://avatars.mds.yandex.net/i?id=df6f0a9b59a9738610c34882daea14963b51056a-8249766-images-thumbs&n=13",
                title = "title",
                subtitle = "test",
                button = ListButton(
                    title = "test"
                )
            ),
            ListElement(
                id = 1,
                image = "https://avatars.mds.yandex.net/i?id=df6f0a9b59a9738610c34882daea14963b51056a-8249766-images-thumbs&n=13",
                title = "title",
                subtitle = "test",
                button = ListButton(
                    title = "test"
                )
            ),
            ListElement(
                id = 2,
                image = "https://avatars.mds.yandex.net/i?id=df6f0a9b59a9738610c34882daea14963b51056a-8249766-images-thumbs&n=13",
                title = "title",
                subtitle = "test",
                button = ListButton(
                    title = "test"
                )
            ),
            ListElement(
                id = 3,
                image = "https://avatars.mds.yandex.net/i?id=df6f0a9b59a9738610c34882daea14963b51056a-8249766-images-thumbs&n=13",
                title = "title",
                subtitle = "test",
                button = ListButton(
                    title = "test"
                )
            )
        )
    }

    override suspend fun getElement(id: Long): ListElement {
        return ListElement(
            id = 0,
            image = "https://avatars.mds.yandex.net/i?id=df6f0a9b59a9738610c34882daea14963b51056a-8249766-images-thumbs&n=13",
            title = "title",
            subtitle = "test",
            button = ListButton(
                title = "test"
            )
        )
    }
}