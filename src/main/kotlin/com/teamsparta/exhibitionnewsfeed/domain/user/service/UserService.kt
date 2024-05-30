package com.teamsparta.exhibitionnewsfeed.domain.user.service

import com.teamsparta.exhibitionnewsfeed.domain.user.dto.*

interface UserService {
    fun signUp(request: SignUpRequest): SignUpResponse
    fun login(request: LoginRequest): LoginResponse
    fun getProfile(userId: Long): UserProfileResponse
}
