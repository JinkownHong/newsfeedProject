package com.teamsparta.exhibitionnewsfeed.domain.user.controller

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.RequestUser
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.UpdateUserProfileRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.UserProfileResponse
import com.teamsparta.exhibitionnewsfeed.domain.user.service.UserService
import com.teamsparta.exhibitionnewsfeed.exception.UnauthorizedException
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1")
@RestController
class UserController(
    private val userService: UserService
) {
    @GetMapping("/profile/{userId}")
    fun getProfile(@PathVariable userId: Long): ResponseEntity<UserProfileResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.getProfile(userId))
    }

    @PatchMapping("/profile/{userId}")
    fun updateProfile(
        @PathVariable userId: Long,
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
        @RequestBody request: UpdateUserProfileRequest
    ): ResponseEntity<UserProfileResponse> {
        if (userId != authUser.id) throw UnauthorizedException("권한이 없습니다.")
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateProfile(userId, request))
    }
}