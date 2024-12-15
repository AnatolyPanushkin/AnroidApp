package com.example.data.network.response

import com.example.domain.data.entity.ListElement

data class ElementsResponse(
    val data: ElementsData
)

data class ElementsData(
    val elements: List<ListElement>
)