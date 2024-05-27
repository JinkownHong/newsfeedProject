package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.model

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.Post
import com.teamsparta.exhibitionnewsfeed.domain.user.model.Users
import jakarta.persistence.*

@Entity
class CommentLike (
    @ManyToOne
    val comment: Comment,

    @ManyToOne
    val users : Users
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}