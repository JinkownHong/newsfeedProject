package com.teamsparta.exhibitionnewsfeed.domain.user.service

import com.teamsparta.exhibitionnewsfeed.domain.user.dto.SignUpRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.SignUpResponse
import com.teamsparta.exhibitionnewsfeed.domain.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {
    override fun signUp(request: SignUpRequest): SignUpResponse {
        if (userRepository.existsByEmail(request.email)) throw IllegalStateException("User already exists")
        return userRepository.save(request.toEntity(passwordEncoder)).toResponse()
    }
}