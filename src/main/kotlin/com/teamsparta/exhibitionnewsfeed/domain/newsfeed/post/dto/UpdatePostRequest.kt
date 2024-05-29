package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto

data class UpdatePostRequest(
    val title: String,

    val content: String,

    val userId: Long
)
