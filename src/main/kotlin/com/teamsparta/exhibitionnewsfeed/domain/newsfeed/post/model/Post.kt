package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model

import com.teamsparta.exhibitionnewsfeed.domain.likes.model.PostLike
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.model.Comment
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.UpdatePostRequest
import com.teamsparta.exhibitionnewsfeed.domain.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "post")
class Post(
    @Column(name = "title")
    var title: String,

    @Column(name = "content")
    var content: String,

    @ManyToOne @JoinColumn(name = "user_id")
    var users: User,

    @OneToMany(mappedBy = "post", cascade = [(CascadeType.ALL)])
    val comments: List<Comment> = emptyList(),

    @OneToMany(mappedBy = "post", cascade = [(CascadeType.ALL)])
    var postTag: MutableList<PostTag> = mutableListOf(),
) : BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    val likes: MutableList<PostLike> = mutableListOf()

    fun updatePostField(request: UpdatePostRequest) {
        title = request.title
        content = request.content
    }

    fun hashTagList(hashTag: String): List<String> {
        var tagList = mutableListOf<String>()
        if ("^[\\sㄱ-ㅎ가-힣a-zA-Z0-9,]*$".toRegex().matches(hashTag)) {
            hashTag.split(",").forEach {
                val s = it.trim()
                if (s.isNotBlank()) tagList.add(s)
            }
        } else {
            return throw IllegalArgumentException("잘못된 형식의 태그입니다.(특수문자 불가, 구분자: 쉼표(,))")
        }
        return tagList
    }
}