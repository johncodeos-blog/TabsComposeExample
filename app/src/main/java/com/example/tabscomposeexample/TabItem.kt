package com.example.tabscomposeexample

import androidx.compose.runtime.Composable

typealias ComposableFun = @Composable () -> Unit

sealed class TabItem(var icon: Int, var title: String, var screen: ComposableFun) {
    object Music : TabItem(R.drawable.ic_music, "Music", { MusicScreen() })
    object Movies : TabItem(R.drawable.ic_movie, "Movies", { MoviesScreen() })
    object Books : TabItem(R.drawable.ic_book, "Books", { BooksScreen() })
}