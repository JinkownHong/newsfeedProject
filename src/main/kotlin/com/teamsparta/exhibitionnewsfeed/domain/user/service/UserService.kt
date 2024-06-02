package com.teamsparta.exhibitionnewsfeed.domain.user.service

import com.teamsparta.exhibitionnewsfeed.domain.user.dto.UpdateUserProfileRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.UserProfileResponse

interface UserService {
    fun getProfile(userId: Long): UserProfileResponse
    fun updateProfile(userId: Long, request: UpdateUserProfileRequest): UserProfileResponse
}
