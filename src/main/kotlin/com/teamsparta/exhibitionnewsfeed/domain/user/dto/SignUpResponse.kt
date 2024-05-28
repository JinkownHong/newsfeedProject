package com.teamsparta.exhibitionnewsfeed.domain.user.dto

import com.teamsparta.exhibitionnewsfeed.domain.user.model.User

data class SignUpResponse(
    val id: Long,
    val email: String,
    val nickname: String,
) {
    companion object {
        fun from(user: User) = SignUpResponse(
            id = user.id ?: throw IllegalStateException("User ID cannot be null"),
            email = user.email,
            nickname = user.nickname
        )
    }
}
