package com.teamsparta.exhibitionnewsfeed.domain.auth.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class RefreshToken(
    @Id
    val refreshToken: String,

    @Column(nullable = false)
    val userId: Long
)