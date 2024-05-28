package com.teamsparta.exhibitionnewsfeed.domain.user.service

import com.teamsparta.exhibitionnewsfeed.domain.user.dto.LoginRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.LoginResponse
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.SignUpRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.SignUpResponse

interface UserService {
    fun signUp(request: SignUpRequest): SignUpResponse
    fun login(request: LoginRequest): LoginResponse
}
