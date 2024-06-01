package com.teamsparta.exhibitionnewsfeed.domain.user.service

import com.teamsparta.exhibitionnewsfeed.domain.user.dto.SignUpRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.SignUpResponse
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.UpdateUserProfileRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.UserProfileResponse

interface UserService {
    fun signUp(request: SignUpRequest): SignUpResponse
    fun getProfile(userId: Long): UserProfileResponse
    fun updateProfile(userId: Long, request: UpdateUserProfileRequest): UserProfileResponse
}
