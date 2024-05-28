package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.service

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.PostResponse

interface PostService {
    fun getPostById(postId: Long): PostResponse
}