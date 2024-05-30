package com.teamsparta.exhibitionnewsfeed.domain.user.dto

data class UpdateUserProfileRequest(
    val nickname: String,
    val description: String?,
    val password: String
)
