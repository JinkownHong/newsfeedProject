package com.teamsparta.exhibitionnewsfeed.domain.likes.repository

import com.teamsparta.exhibitionnewsfeed.domain.likes.model.PostLike
import org.springframework.data.jpa.repository.JpaRepository

interface PostLikeRepository : JpaRepository<PostLike, Long> {
    fun findByPostIdAndUserId(postId: Long, userId: Long): PostLike?

    fun findByPostId(postId: Long): List<PostLike>
}