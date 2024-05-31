package com.teamsparta.exhibitionnewsfeed.domain.auth.controller

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.RequestUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.service.AuthService
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.LoginRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.LoginResponse
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
}