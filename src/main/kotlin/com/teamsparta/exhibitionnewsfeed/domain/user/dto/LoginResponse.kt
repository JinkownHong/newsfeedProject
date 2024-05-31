package com.teamsparta.exhibitionnewsfeed.domain.user.dto

import com.teamsparta.exhibitionnewsfeed.domain.user.model.User

data class LoginResponse(
    val user: UserResponse,
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun from(user: User, accessToken: String, refreshToken: String) =
            LoginResponse(
                user = user.toResponse(),
                accessToken = accessToken,
                refreshToken = refreshToken
            )
    }
}

