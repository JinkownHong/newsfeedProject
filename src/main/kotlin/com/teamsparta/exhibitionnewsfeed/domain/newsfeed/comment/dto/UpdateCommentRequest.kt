package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto

import jakarta.validation.constraints.NotBlank

data class UpdateCommentRequest(
    val userId: Long,
    @field:NotBlank
    val content: String,

    )
