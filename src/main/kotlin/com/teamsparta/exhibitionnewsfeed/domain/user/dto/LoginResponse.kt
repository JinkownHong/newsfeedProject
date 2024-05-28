package com.teamsparta.exhibitionnewsfeed.domain.user.dto

import com.teamsparta.exhibitionnewsfeed.domain.user.model.User

data class LoginResponse(
    val user: UserResponse
    //TODO token 정보
) {
    companion object {
        fun from(user: User) = LoginResponse(user = user.toResponse())
    }
}

