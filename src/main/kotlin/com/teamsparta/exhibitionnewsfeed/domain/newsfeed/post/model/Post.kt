package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.model.Comment
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.UpdatePostRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.model.User
import com.teamsparta.exhibitionnewsfeed.exception.ComparativeVerificationException
import jakarta.persistence.*

@Entity
@Table(name = "post")
class Post(

    @Column(name = "title")
    var title: String,

    @Column(name = "content")
    var content: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,

    @OneToMany(mappedBy = "post", cascade = [(CascadeType.ALL)])
    val comments: List<Comment> = emptyList(),

    @OneToMany(mappedBy = "post", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    var postTag: MutableList<PostTag> = mutableListOf(),
) : BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun updatePostField(request: UpdatePostRequest) {
        title = request.title
        content = request.content
        if (user.id != request.userId) {
            throw ComparativeVerificationException(request.userId)
        }
    }
}