package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.repository

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.HashTag
import org.springframework.data.jpa.repository.JpaRepository

interface HashTagRepository : JpaRepository<HashTag, Long> {
}