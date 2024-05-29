package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.Post
import com.teamsparta.exhibitionnewsfeed.domain.user.model.User
import java.time.LocalDateTime

data class PostsResponse(
    val id: Long,
    val title: String,
    val content: String,
    val user: User,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(post: Post): PostsResponse {
            return PostsResponse(
                id = post.id ?: throw IllegalStateException("ID cannot be Null"),
                title = post.title,
                content = post.content,
                createdAt = post.createdAt,
                user = post.user,
            )
        }
    }
}
