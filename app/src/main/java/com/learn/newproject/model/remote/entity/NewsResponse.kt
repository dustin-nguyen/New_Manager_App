package com.learn.newproject.model.remote.entity

import com.learn.newproject.model.entity.New

data class NewsResponse(
    val news: List<New>,
    val page: Int,
    val status: String
)