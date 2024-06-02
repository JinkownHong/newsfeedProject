package com.teamsparta.exhibitionnewsfeed.domain.auth.service

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.dto.LoginRequest
import com.teamsparta.exhibitionnewsfeed.domain.auth.dto.LoginResponse
import com.teamsparta.exhibitionnewsfeed.domain.auth.dto.SignUpRequest
import com.teamsparta.exhibitionnewsfeed.domain.auth.dto.SignUpResponse

interface AuthService {
    fun signUp(request: SignUpRequest): SignUpResponse
    fun login(request: LoginRequest): LoginResponse
    fun getNewAccessToken(token: String): String
    fun logout(authUser: AuthUser)
    fun verifyPassword(userId: Long, password: String?): Boolean
}
