package com.teamsparta.exhibitionnewsfeed.domain.user.service

import com.teamsparta.exhibitionnewsfeed.auth.JwtTokenProvider
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.*
import com.teamsparta.exhibitionnewsfeed.domain.user.repository.UserRepository
import com.teamsparta.exhibitionnewsfeed.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
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

        val token = jwtTokenProvider.generateToken(user)
        return LoginResponse.from(user, token)
    }

    override fun getProfile(userId: Long): UserProfileResponse {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw ModelNotFoundException("User id $userId not found.", userId)
        return UserProfileResponse.from(user)
    }
}