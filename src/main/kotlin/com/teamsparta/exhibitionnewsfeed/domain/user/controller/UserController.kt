package com.teamsparta.exhibitionnewsfeed.domain.user.controller

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.RequestUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.TokenType
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.SignUpRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.SignUpResponse
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.UpdateUserProfileRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.UserProfileResponse
import com.teamsparta.exhibitionnewsfeed.domain.user.service.UserService
import com.teamsparta.exhibitionnewsfeed.exception.UnauthorizedException
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1")
@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping("/sign-up")
    fun signUp(@RequestBody @Valid request: SignUpRequest): ResponseEntity<SignUpResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userService.signUp(request))
    }

    @GetMapping("/profile/{userId}")
    fun getProfile(@PathVariable userId: Long): ResponseEntity<UserProfileResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.getProfile(userId))
    }

    @PostMapping("/profile/{userId}/verify")
    fun verifyPassword(
        @PathVariable userId: Long,
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
        @RequestBody request: Map<String, String>
    ): ResponseEntity<Any> {
        checkAuth(userId, authUser)
        if (!userService.verifyPassword(userId, request["password"])) throw UnauthorizedException("비밀번호가 일치하지 않습니다.")

        //TODO 암호화된 검증 토큰 response에 넣기
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @PatchMapping("/profile/{userId}")
    fun updateProfile(
        @PathVariable userId: Long,
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
        @RequestBody request: UpdateUserProfileRequest
    ): ResponseEntity<UserProfileResponse> {
        checkAuth(userId, authUser)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateProfile(userId, request))
    }

    private fun checkAuth(userId: Long, authUser: AuthUser) {
        if (userId != authUser.id) throw UnauthorizedException("권한이 없습니다.")
        if (authUser.tokenType != TokenType.ACCESS_TOKEN) throw UnauthorizedException("유효한 토큰이 아닙니다.")
    }
}