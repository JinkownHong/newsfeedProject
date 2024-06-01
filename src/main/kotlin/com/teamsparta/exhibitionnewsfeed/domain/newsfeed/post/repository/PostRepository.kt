package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.repository

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PostRepository : JpaRepository<Post, Long> {
    fun findAllByOrderByCreatedAtDesc(): List<Post>

    @Query("select p from Post p left join PostTag pt on p.id = pt.post.id left join HashTag h on pt.hashTag.id = h.id where h.tagName = ?1")
    fun findByTagName(tagName: String): List<Post>
}

