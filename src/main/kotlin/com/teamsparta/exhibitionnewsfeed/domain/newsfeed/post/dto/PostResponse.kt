package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CommentResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.Post
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.UserResponse
import java.time.LocalDateTime

data class PostResponse(
    val title: String,
    val content: String,
    val heartStatus: Boolean,
    val createdAt: LocalDateTime?,
    val updateAt: LocalDateTime?,
    val user: UserResponse,
    val postTag: List<PostTagResponse>,
    val comments: List<CommentResponse>,
    val likeCount: Int,
) {
    companion object {
        fun from(post: Post): PostResponse {
            return PostResponse(
                post.title,
                post.content,
                post.heartStatus,
                post.createdAt,
                post.updatedAt,
                post.users.toResponse(),
                post.postTag.map { PostTagResponse.from(it) },
                post.comments.map { CommentResponse.from(it) },
                post.likes.size
            )
        }
    }
}