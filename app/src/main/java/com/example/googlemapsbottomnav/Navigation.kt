package com.example.googlemapsbottomnav

import androidx.annotation.StringRes

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Map: Screen("map", R.string.map)
    object Other: Screen("other", R.string.other)
}

val bottomNavItems = listOf(
    Screen.Map,
    Screen.Other
)