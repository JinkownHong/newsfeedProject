package com.teamsparta.exhibitionnewsfeed.domain.auth.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class RefreshToken(
    @Id
    val refreshToken: String
)