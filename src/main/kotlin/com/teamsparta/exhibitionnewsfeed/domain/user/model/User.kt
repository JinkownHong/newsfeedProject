package com.teamsparta.exhibitionnewsfeed.domain.user.model

import com.teamsparta.exhibitionnewsfeed.domain.user.dto.SignUpResponse
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User (
    @Column(name = "email")
    val email: String,

    @Column(name = "password")
    val password : String,

    @Column(name = "nickname")
    var nickname : String,

    @Column(name = "description", nullable = true)
    var description : String?,
)
{
    fun toResponse(): SignUpResponse {
        return SignUpResponse(
            id = this.id ?: throw IllegalStateException("User ID cannot be null"),
            email = this.email,
            nickname = this.nickname,
        )
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}