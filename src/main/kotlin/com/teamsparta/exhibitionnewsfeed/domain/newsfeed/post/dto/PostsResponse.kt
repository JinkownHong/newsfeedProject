package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.Post
import java.time.LocalDateTime

data class PostsResponse(
    val id: Long,
    val title: String,
    val content: String,
    val user: String,
    val createdAt: LocalDateTime?,
    val postTag: List<PostTagResponse>
) {
    companion object {
        fun from(post: Post): PostsResponse {
            return PostsResponse(
                post.id ?: throw IllegalStateException("ID cannot be Null"),
                post.title,
                post.content,
                post.user.toResponse().nickname,
                post.createdAt,
                post.postTag.map { PostTagResponse.from(it) }
            )
        }
    }
}