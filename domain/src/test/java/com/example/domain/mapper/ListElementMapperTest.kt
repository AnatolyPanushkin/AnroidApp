package com.example.domain.mapper

import com.example.domain.data.entity.ListButton
import com.example.domain.data.entity.ListElement
import com.example.domain.data.repository.LocalStorageRepository
import com.example.domain.entity.ListElementEntity
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ListElementMapperTest {

    @Test
    fun map() {
        val localStorage = mockk<LocalStorageRepository>()
        every { localStorage.isLiked(1) } returns true
        every { localStorage.isLiked(2) } returns false
        val mapper = ListElementMapper(localStorage)

        val case1 = assertElements(mapper, 1)
        val case2 = assertElements(mapper, 2)
        assertEquals(case1.first, case1.second)
        assertTrue(case2.first != case2.second)
    }

    private fun assertElements(mapper: ListElementMapper, id: Long): Pair<ListElementEntity, ListElementEntity> {
        val actual = mapper.map(
            ListElement(
                id = id,
                image = "https://avatars.mds.yandex.net/i?id=df6f0a9b59a9738610c34882daea14963b51056a-8249766-images-thumbs&n=13",
                title = "title",
                subtitle = "test",
                button = ListButton(
                    title = "test"
                )
            )
        )
        val expect = ListElementEntity(
            id = id,
            image = "https://avatars.mds.yandex.net/i?id=df6f0a9b59a9738610c34882daea14963b51056a-8249766-images-thumbs&n=13",
            title = "title",
            subtitle = "test",
            button = ListButton(
                title = "test"
            ),
            like = true
        )
        return Pair(actual, expect)
    }
}
