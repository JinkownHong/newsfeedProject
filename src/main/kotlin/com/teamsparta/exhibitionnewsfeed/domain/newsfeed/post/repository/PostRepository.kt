package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.repository

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
    fun findAllByOrderByCreatedAtDesc(): List<Post>
}

