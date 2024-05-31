package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.HashTag
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.Post
import com.teamsparta.exhibitionnewsfeed.domain.user.model.User
import jakarta.validation.constraints.NotBlank


data class CreatePostRequest(

    @field:NotBlank
    val title: String,

    @field:NotBlank
    val content: String,

    val userId: Long,

    val tagName: String
)

fun CreatePostRequest.toEntity(user: User): Post {
    return Post(
        title = this.title,
        content = this.content,
        users = user //TODO: 로그인 구현 후 다른방식으로 수정
    )
}

fun toHashTagEntity(tagName: String): HashTag {
    return HashTag(
        tagName = tagName
    )
}