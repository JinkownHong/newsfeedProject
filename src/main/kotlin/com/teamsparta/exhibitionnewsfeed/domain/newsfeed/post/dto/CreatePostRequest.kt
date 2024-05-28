package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.Post
import com.teamsparta.exhibitionnewsfeed.domain.user.model.User


data class CreatePostRequest(
    val title: String,
    val content: String,
)

fun CreatePostRequest.toEntity(user: User): Post {
    return Post(
        title = this.title,
        content = this.content,
        user = user // 로그인 구현 후 다른방식으로 수정
    )

}


