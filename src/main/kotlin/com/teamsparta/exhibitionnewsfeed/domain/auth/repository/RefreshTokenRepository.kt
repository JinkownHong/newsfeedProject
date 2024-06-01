package com.teamsparta.exhibitionnewsfeed.domain.auth.repository

import com.teamsparta.exhibitionnewsfeed.domain.auth.model.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository : JpaRepository<RefreshToken, String> {
    fun existsByRefreshToken(refreshToken: String): Boolean
    fun deleteByUserId(id: Long)
}