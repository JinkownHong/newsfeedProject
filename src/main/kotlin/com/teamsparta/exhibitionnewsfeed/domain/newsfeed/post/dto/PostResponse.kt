package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CommentResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.model.Comment
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.Post
import com.teamsparta.exhibitionnewsfeed.domain.user.model.Users
import java.time.LocalDateTime

data class PostResponse(
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val user: Users,
    val comments: List<CommentResponse>
) {
    companion object {
        fun from(post: Post): PostResponse {
            return PostResponse(
                post.title,
                post.content,
                post.createdAt,
                post.users,
                post.comments.map { CommentResponse.from(it) })
        }
    }
}