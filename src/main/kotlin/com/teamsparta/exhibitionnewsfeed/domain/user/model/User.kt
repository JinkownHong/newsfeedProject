package com.teamsparta.exhibitionnewsfeed.domain.user.model

import com.teamsparta.exhibitionnewsfeed.domain.user.dto.UpdateUserProfileRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.UserResponse
import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
@Table(name = "users")
class User(
    @Column(name = "email")
    val email: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "nickname")
    var nickname: String,

    @Column(name = "description", nullable = true)
    var description: String?,
) {
    fun toResponse(): UserResponse {
        return UserResponse(
            id = this.id ?: throw IllegalStateException("User ID cannot be null"),
            nickname = this.nickname,
        )
    }

    fun isValidPassword(rawPassword: String, passwordEncoder: PasswordEncoder): Boolean {
        return passwordEncoder.matches(rawPassword, this.password)
    }

    fun update(request: UpdateUserProfileRequest, passwordEncoder: PasswordEncoder) {
        nickname = request.nickname
        description = request.description
        password = passwordEncoder.encode(request.password)
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}