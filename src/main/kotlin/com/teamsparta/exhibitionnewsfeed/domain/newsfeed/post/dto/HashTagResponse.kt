package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.HashTag

data class HashTagResponse(
    val id: Long,
    val tagName: String
) {
    companion object {
        fun from(hashTag: HashTag): HashTagResponse {
            return HashTagResponse(
                hashTag.id ?: throw IllegalStateException("User ID cannot be null"),
                hashTag.tagName
            )
        }
    }
}