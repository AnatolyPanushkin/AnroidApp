package com.example.myapplication.details

import com.example.myapplication.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data class DetailsScreenRoute(
    val id: Long
) : Route