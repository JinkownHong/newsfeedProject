package com.teamsparta.exhibitionnewsfeed.domain.user.service

import com.teamsparta.exhibitionnewsfeed.domain.user.dto.SignUpRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.SignUpResponse
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.UpdateUserProfileRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.UserProfileResponse
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
) : UserService {

    @Transactional
    override fun signUp(request: SignUpRequest): SignUpResponse {
        if (userRepository.existsByEmail(request.email)) throw IllegalStateException("User already exists")
        return SignUpResponse.from(userRepository.save(request.toEntity(passwordEncoder)))
    }

    override fun getProfile(userId: Long): UserProfileResponse {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw ModelNotFoundException("User id $userId not found.", userId)
        return UserProfileResponse.from(user)
    }

    override fun verifyPassword(userId: Long, password: String?): Boolean {
        val user =
            userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User id $userId not found.", userId)
        return user.isValidPassword(password ?: "", passwordEncoder)
    }

    @Transactional
    override fun updateProfile(userId: Long, request: UpdateUserProfileRequest): UserProfileResponse {
        val user =
            userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User id $userId not found.", userId)
        user.update(request, passwordEncoder)
        return UserProfileResponse.from(user)
    }
}