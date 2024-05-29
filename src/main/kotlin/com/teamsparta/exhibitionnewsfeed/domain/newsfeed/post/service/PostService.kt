package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.service

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.CreatePostRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.PostResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.PostsResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.UpdatePostRequest


interface PostService {
    fun getPostById(postId: Long): PostResponse
    fun getAllPosts(): List<PostsResponse>
    fun createPost(request: CreatePostRequest): PostsResponse
    fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse
}