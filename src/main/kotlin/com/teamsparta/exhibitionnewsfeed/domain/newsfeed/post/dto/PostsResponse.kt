package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.Post
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.UserResponse
import java.time.LocalDateTime

data class PostsResponse(
    val id: Long,
    val title: String,
    val content: String,
    val users: UserResponse,
    val createdAt: LocalDateTime?,
    val likeCount: Int,
) {
    companion object {
        fun Post.toResponse(): PostsResponse {
            return PostsResponse(
                id = id ?: throw IllegalStateException("ID cannot be Null"),
                title = title,
                content = content,
                createdAt = createdAt,
                users = users.toResponse(),
                likeCount = likes.size
            )
        }
    }
}
