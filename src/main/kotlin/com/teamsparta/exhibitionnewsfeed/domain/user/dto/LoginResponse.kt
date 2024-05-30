package com.teamsparta.exhibitionnewsfeed.domain.user.dto

import com.teamsparta.exhibitionnewsfeed.domain.user.model.User

data class LoginResponse(
    val user: UserResponse,
    val accessToken: String
) {
    companion object {
        fun from(user: User, token: String) = LoginResponse(user = user.toResponse(), accessToken = token)
    }
}

