package com.example.baseprojectflamingo.ui.screen.frg.news

import com.example.baseprojectflamingo.R

object NewsUtils {

    fun getNewsHorizontalList() = mutableListOf(
        News(0, R.string.post_horizontal_title_1, "Author 1",R.string.post_horizontal_content_1, "12:12 - 12/12/2012", R.drawable.news_sport, "SPORT", R.drawable.author_1),
        News(1, R.string.post_horizontal_title_2, "Author 2",R.string.post_horizontal_content_2, "12:12 - 12/12/2012", R.drawable.news_photograph, "PHOTOGRAPHIC", R.drawable.author_2),
        News(2, R.string.post_horizontal_title_3, "Author 3",R.string.post_horizontal_content_3, "12:12 - 12/12/2012", R.drawable.news_tech, "TECHNOLOGY", R.drawable.author_3),
        News(3, R.string.post_horizontal_title_4, "Author 4",R.string.post_horizontal_content_4, "12:12 - 12/12/2012", R.drawable.news_education, "EDUCATION", R.drawable.author_4),
        News(4, R.string.post_horizontal_title_5, "Author 5",R.string.post_horizontal_content_5, "12:12 - 12/12/2012", R.drawable.news_travel, "TRAVELING", R.drawable.author_5),
    )

    fun getNewsVerticalList() = mutableListOf(
        News(0, R.string.post_vertical_title_1, "Author 1",R.string.post_vertical_content_1, "12:12 - 12/12/2012", R.drawable.news_sport, "SPORT", R.drawable.author_6),
        News(1, R.string.post_vertical_title_2, "Author 2",R.string.post_vertical_content_2, "12:12 - 12/12/2012", R.drawable.news_photograph, "PHOTOGRAPHIC", R.drawable.author_7),
        News(2, R.string.post_vertical_title_3, "Author 3",R.string.post_vertical_content_3, "12:12 - 12/12/2012", R.drawable.news_tech, "TECHNOLOGY", R.drawable.author_8),
        News(3, R.string.post_vertical_title_4, "Author 4",R.string.post_vertical_content_4, "12:12 - 12/12/2012", R.drawable.news_education, "EDUCATION", R.drawable.author_9),
        News(4, R.string.post_vertical_title_5, "Author 5",R.string.post_vertical_content_5, "12:12 - 12/12/2012", R.drawable.news_travel, "TRAVELING", R.drawable.author_10),
    )
}