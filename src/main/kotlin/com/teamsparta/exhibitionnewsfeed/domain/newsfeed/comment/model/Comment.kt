package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.model

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.BaseTime
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.Post
import com.teamsparta.exhibitionnewsfeed.domain.user.model.User
import jakarta.persistence.*

@Entity
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "content")
    var content: String,

    @ManyToOne
    val user: User,

    @ManyToOne
    val post: Post,

    ) : BaseTime() {
}


