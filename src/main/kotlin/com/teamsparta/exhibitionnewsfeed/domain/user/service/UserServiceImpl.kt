package com.teamsparta.exhibitionnewsfeed.domain.user.service

import com.teamsparta.exhibitionnewsfeed.domain.user.dto.LoginRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.LoginResponse
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.SignUpRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.SignUpResponse
import com.teamsparta.exhibitionnewsfeed.domain.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    @Transactional
    override fun signUp(request: SignUpRequest): SignUpResponse {
        if (userRepository.existsByEmail(request.email)) throw IllegalStateException("User already exists")
        return SignUpResponse.from(userRepository.save(request.toEntity(passwordEncoder)))
    }

    override fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(request.email) ?: throw IllegalArgumentException("잘못된 Email/PW 입니다.")
        if (!user.isValidPassword(
                request.password,
                passwordEncoder
            )
        ) throw IllegalArgumentException("잘못된 Email/PW 입니다.")
        return LoginResponse.from(user)
    }
}