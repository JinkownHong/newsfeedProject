package com.teamsparta.exhibitionnewsfeed.domain.auth.dto

import com.teamsparta.exhibitionnewsfeed.domain.user.model.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import org.springframework.security.crypto.password.PasswordEncoder

data class SignUpRequest(

    @field:Email
    @field:NotBlank
    val email: String,

    @field:Length(min = 6, max = 30)
    val password: String,

    @field:Length(min = 2, max = 15)
    val nickname: String,

    val description: String?
) {
    fun toEntity(passwordEncoder: PasswordEncoder): User {
        return User(
            email = this.email,
            password = passwordEncoder.encode(this.password),
            nickname = this.nickname,
            description = this.description
        )
    }
}
