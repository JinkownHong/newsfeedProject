package com.teamsparta.exhibitionnewsfeed.domain.user.dto

import com.teamsparta.exhibitionnewsfeed.domain.user.model.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

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
    fun toEntity(): User {
        return User(
            email = this.email,
            password = this.password,
            nickname = this.nickname,
            description = this.description
        )
    }
}
