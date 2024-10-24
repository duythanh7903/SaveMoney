package com.example.baseprojectflamingo.ui.screen.frg.news

import java.io.Serializable

data class News(
    var id: Long = 0L,
    var titleRes: Int = 0,
    var authorName: String = "",
    var contentRes: Int = 0,
    var createdAt: String = "",
    var imageRes: Int = 0,
    var category: String = "",
    var avatarAuthorRes: Int = 0
) : Serializable{
}