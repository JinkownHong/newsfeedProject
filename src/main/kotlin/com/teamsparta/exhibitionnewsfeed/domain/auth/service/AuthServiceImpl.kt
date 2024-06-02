package com.teamsparta.exhibitionnewsfeed.domain.auth.service

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.JwtTokenProvider
import com.teamsparta.exhibitionnewsfeed.domain.auth.TokenType
import com.teamsparta.exhibitionnewsfeed.domain.auth.dto.LoginRequest
import com.teamsparta.exhibitionnewsfeed.domain.auth.dto.LoginResponse
import com.teamsparta.exhibitionnewsfeed.domain.auth.dto.SignUpRequest
import com.teamsparta.exhibitionnewsfeed.domain.auth.dto.SignUpResponse
import com.teamsparta.exhibitionnewsfeed.domain.auth.model.RefreshToken
import com.teamsparta.exhibitionnewsfeed.domain.auth.repository.RefreshTokenRepository
import com.teamsparta.exhibitionnewsfeed.domain.user.repository.UserRepository
import com.teamsparta.exhibitionnewsfeed.exception.ModelNotFoundException
import com.teamsparta.exhibitionnewsfeed.exception.UnauthorizedException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthServiceImpl(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
) : AuthService {

    @Transactional
    override fun signUp(request: SignUpRequest): SignUpResponse {
        if (userRepository.existsByEmail(request.email)) throw IllegalStateException("이미 존재하는 회원입니다.")
        return SignUpResponse.from(userRepository.save(request.toEntity(passwordEncoder)))
    }

    @Transactional
    override fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(request.email) ?: throw IllegalArgumentException("잘못된 Email/PW 입니다.")
        if (!user.isValidPassword(
                request.password,
                passwordEncoder
            )
        ) throw IllegalArgumentException("잘못된 Email/PW 입니다.")

        val refreshToken = jwtTokenProvider.generateRefreshToken(user)
        val accessToken = jwtTokenProvider.generateAccessToken(user)

        refreshTokenRepository.save(
            RefreshToken(
                refreshToken,
                user.id ?: throw IllegalStateException("User Id must be not null")
            )
        )
        return LoginResponse.from(user, accessToken, refreshToken)
    }

    override fun getNewAccessToken(token: String): String {
        if (!jwtTokenProvider.validateToken(token)) {
            throw UnauthorizedException("Invalid Token")
        }
        if (jwtTokenProvider.getSubject(token) != TokenType.REFRESH_TOKEN.name) throw IllegalArgumentException("REFRESH_TOKEN이 아닙니다.")

        val userId = jwtTokenProvider.getUserId(token)

        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException(
            "존재하지 않는 User. user id: $userId",
            userId
        )
        if (!refreshTokenRepository.existsByRefreshToken(token)) throw UnauthorizedException("유효한 토큰이 아닙니다.")
        return jwtTokenProvider.generateAccessToken(user)
    }

    @Transactional
    override fun logout(authUser: AuthUser) {
        refreshTokenRepository.deleteByUserId(authUser.id)
    }

    override fun verifyPassword(userId: Long, password: String?): Boolean {
        val user =
            userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User id $userId not found.", userId)
        return user.isValidPassword(password ?: "", passwordEncoder)
    }
}