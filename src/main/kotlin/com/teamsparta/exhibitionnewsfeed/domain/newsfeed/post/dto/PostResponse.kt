package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CommentResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.Post
import com.teamsparta.exhibitionnewsfeed.domain.user.model.User
import java.time.LocalDateTime

data class PostResponse(
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val user: User,
    val comments: List<CommentResponse>
) {
    companion object {
        fun from(post: Post): PostResponse {
            return PostResponse(
                post.title,
                post.content,
                post.createdAt,
                post.user,
                post.comments.map { CommentResponse.from(it) })
        }
    }
}