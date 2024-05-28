package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.model.Comment
import com.teamsparta.exhibitionnewsfeed.domain.user.model.Users
import java.time.LocalDateTime

data class CommentResponse(
    val content: String,
    val createdAt: LocalDateTime,
    val user: Users
) {
    companion object {
        fun from(comment: Comment): CommentResponse {
            return CommentResponse(
                comment.content,
                comment.createdAt,
                comment.users
            )
        }
    }
}