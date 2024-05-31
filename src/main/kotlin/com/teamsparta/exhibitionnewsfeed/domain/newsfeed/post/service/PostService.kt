package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.service

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.CreatePostRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.PostResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.PostsResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.UpdatePostRequest


interface PostService {
    fun getPostById(postId: Long, authUser: AuthUser): PostResponse
    fun getAllPosts(authUser: AuthUser): List<PostsResponse>
    fun createPost(authUser: AuthUser, request: CreatePostRequest): PostsResponse
    fun updatePost(postId: Long, authUser: AuthUser, request: UpdatePostRequest): PostResponse
    fun deletePost(postId: Long, authUser: AuthUser)
    fun getFilteredPosts(authUser: AuthUser, tagName: String): List<PostsResponse>
}