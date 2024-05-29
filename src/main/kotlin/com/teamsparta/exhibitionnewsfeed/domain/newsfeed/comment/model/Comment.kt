package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.model

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CommentResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.Post
import com.teamsparta.exhibitionnewsfeed.domain.user.dto.UserResponse
import com.teamsparta.exhibitionnewsfeed.domain.user.model.User
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "content")
    var content: String,

    @CreationTimestamp
    @Column(updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    val user: User,

    @ManyToOne
    val post: Post,


    ) {


    fun toResponse(): CommentResponse {
        return CommentResponse(
            id = this.id ?: throw IllegalStateException("Comment Id cannot be null"),
            postId = this.post.id ?: throw IllegalStateException("Post Id cannot be null"),
            content = this.content,
            createdAt = this.createdAt,
            user = UserResponse(
                id = this.user.id ?: throw IllegalStateException("User Id cannot be null"),
                nickname = this.user.nickname
            )
        )
    }
}


