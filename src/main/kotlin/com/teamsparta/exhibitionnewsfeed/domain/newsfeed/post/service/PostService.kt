package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.service

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.PostResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.CreatePostRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.PostsResponse


interface PostService {
    fun getPostById(postId: Long): PostResponse
    fun createPost(request: CreatePostRequest): PostsResponse
}