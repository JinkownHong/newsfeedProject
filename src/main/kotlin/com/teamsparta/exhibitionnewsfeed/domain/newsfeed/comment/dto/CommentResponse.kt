package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.model.Comment
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.UserResponse
import java.time.LocalDateTime

data class CommentResponse(
    val content: String,
    val createdAt: LocalDateTime,
    val user: UserResponse,
    val id: Long,
    val postId: Long
) {
    companion object {

        fun from(comment: Comment): CommentResponse {
            return CommentResponse(
                id = comment.id ?: 0,
                content = comment.content,
                createdAt = comment.createdAt,
                user = comment.user.toResponse(),
                postId = comment.post.id ?: throw IllegalStateException("postId cannot be null")
            )
        }
    }
}
