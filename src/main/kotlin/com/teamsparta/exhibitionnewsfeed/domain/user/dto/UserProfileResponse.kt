package com.teamsparta.exhibitionnewsfeed.domain.user.dto

import com.teamsparta.exhibitionnewsfeed.domain.user.model.User

data class UserProfileResponse(
    val id: Long,
    val email: String,
    val nickname: String,
    val description: String?
) {
    companion object {
        fun from(user: User) = UserProfileResponse(
            id = user.id ?: throw IllegalStateException("User ID must not be null"),
            email = user.email,
            nickname = user.nickname,
            description = user.description
        )

    }
}
