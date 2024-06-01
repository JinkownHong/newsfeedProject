package com.teamsparta.exhibitionnewsfeed.domain.auth.service

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.LoginRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.LoginResponse

interface AuthService {

    fun login(request: LoginRequest): LoginResponse
    fun getNewAccessToken(token: String): String
    fun logout(authUser: AuthUser)
    fun verifyPassword(userId: Long, password: String?): Boolean
}
