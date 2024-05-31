package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto

import jakarta.validation.constraints.NotBlank

data class UpdatePostRequest(

    @field:NotBlank
    val title: String,

    @field:NotBlank
    val content: String,

    val userId: Long,

    val tagName: String
)
