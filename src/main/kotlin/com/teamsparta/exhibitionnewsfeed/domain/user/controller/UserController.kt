package com.teamsparta.exhibitionnewsfeed.domain.user.controller

import com.teamsparta.exhibitionnewsfeed.domain.user.dto.*
import com.teamsparta.exhibitionnewsfeed.domain.user.service.UserService
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

    @PostMapping("/login")
    fun login(@RequestBody @Valid request: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.login(request))
    }

    @GetMapping("/profile/{userId}")
    fun getProfile(@PathVariable userId: Long): ResponseEntity<UserProfileResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.getProfile(userId))
    }
}