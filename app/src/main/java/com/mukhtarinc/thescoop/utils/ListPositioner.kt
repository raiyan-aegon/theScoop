package com.mukhtarinc.thescoop.utils

interface ListPositioner {

    val recyclerViewScrollKey: String

    fun loadListPosition()

    fun saveListPosition()

    fun resetListPosition()
}