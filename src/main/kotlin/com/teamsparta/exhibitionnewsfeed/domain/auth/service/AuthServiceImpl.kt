package com.teamsparta.exhibitionnewsfeed.domain.auth.service

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.JwtTokenProvider
import com.teamsparta.exhibitionnewsfeed.domain.auth.TokenType
import com.teamsparta.exhibitionnewsfeed.domain.auth.model.RefreshToken
import com.teamsparta.exhibitionnewsfeed.domain.auth.repository.RefreshTokenRepository
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.LoginRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.LoginResponse
import com.teamsparta.exhibitionnewsfeed.domain.user.repository.UserRepository
import com.teamsparta.exhibitionnewsfeed.exception.ModelNotFoundException
import com.teamsparta.exhibitionnewsfeed.exception.UnauthorizedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
) : AuthService {

    override fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(request.email) ?: throw IllegalArgumentException("잘못된 Email/PW 입니다.")
        if (!user.isValidPassword(
                request.password,
                passwordEncoder
            )
        ) throw IllegalArgumentException("잘못된 Email/PW 입니다.")

        val refreshToken = jwtTokenProvider.generateRefreshToken(user)
        val accessToken = jwtTokenProvider.generateAccessToken(user)

        refreshTokenRepository.save(RefreshToken(refreshToken))

        return LoginResponse.from(user, accessToken, refreshToken)
    }

    override fun getNewAccessToken(authUser: AuthUser): String {
        if (authUser.tokenType != TokenType.REFRESH_TOKEN) throw IllegalArgumentException("REFRESH_TOKEN이 아닙니다.")
        val userId = authUser.id
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException(
            "존재하지 않는 User. user id: $userId",
            userId
        )
        if (!refreshTokenRepository.existsByRefreshToken(authUser.token)) throw UnauthorizedException("유효한 토큰이 아닙니다.")
        return jwtTokenProvider.generateAccessToken(user)
    }

    override fun logout(authUser: AuthUser) {
        val refreshToken =
            refreshTokenRepository.findByIdOrNull(authUser.token) ?: throw UnauthorizedException("유효한 토큰이 아닙니다.")
        refreshTokenRepository.delete(refreshToken)
    }

    override fun verifyPassword(userId: Long, password: String?): Boolean {
        val user =
            userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User id $userId not found.", userId)
        return user.isValidPassword(password ?: "", passwordEncoder)
    }
}