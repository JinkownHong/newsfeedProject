package com.teamsparta.exhibitionnewsfeed.domain.auth.controller

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.RequestUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.TokenType
import com.teamsparta.exhibitionnewsfeed.domain.auth.service.AuthService
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.LoginRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.LoginResponse
import com.teamsparta.exhibitionnewsfeed.exception.UnauthorizedException
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/auth")
@RestController
class AuthController(private val authService: AuthService) {
    @PostMapping("/login")
    fun login(@RequestBody @Valid request: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(authService.login(request))
    }

    @PostMapping("/new-token")
    fun getNewAccessToken(@Parameter(hidden = true) @RequestUser authUser: AuthUser): ResponseEntity<Map<String, String>> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(mapOf("accessToken" to authService.getNewAccessToken(authUser)))
    }

    @PostMapping("/logout")
    fun logout(@RequestUser @Valid authUser: AuthUser): ResponseEntity<Void> {
        if (authUser.tokenType != TokenType.REFRESH_TOKEN) throw UnauthorizedException("유효한 토큰이 아닙니다.")
        authService.logout(authUser)
        return ResponseEntity
            .status(HttpStatus.OK)
            .build()
    }

    @PostMapping("/{userId}")
    fun verifyPassword(
        @PathVariable userId: Long,
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
        @RequestBody request: UserPassword
    ): ResponseEntity<Unit> {
        if (userId != authUser.id) throw UnauthorizedException("권한이 없습니다.")
        if (!authService.verifyPassword(userId, request.password)) throw UnauthorizedException("비밀번호가 일치하지 않습니다.")

        //TODO 암호화된 검증 토큰 response에 넣기
        return ResponseEntity.status(HttpStatus.OK).build()
    }
}