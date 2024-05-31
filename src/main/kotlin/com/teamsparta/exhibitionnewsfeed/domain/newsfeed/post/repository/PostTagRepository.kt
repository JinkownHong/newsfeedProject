package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.repository

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.PostTag
import org.springframework.data.jpa.repository.JpaRepository

interface PostTagRepository : JpaRepository<PostTag, Long> {
}