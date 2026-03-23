package com.example.mission365.Screens.navigation
import kotlinx.serialization.Serializable


@Serializable
sealed class Routes{
    @Serializable
    object HomeScreen : Routes()
    @Serializable
    object AgeScreen : Routes()
    @Serializable
    object YearScreen : Routes()
    @Serializable
    object  CustomScreen : Routes()
}
