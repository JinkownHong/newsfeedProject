package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.PostTag

data class PostTagResponse(
    val hashTag: HashTagResponse
) {
    companion object {
        fun from(postTag: PostTag): PostTagResponse {
            return PostTagResponse(HashTagResponse.from(postTag.hashTag))
        }
    }
}

