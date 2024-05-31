package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto

import jakarta.validation.constraints.NotBlank

data class CreateCommentRequest(

    @field:NotBlank
    val content: String
)
