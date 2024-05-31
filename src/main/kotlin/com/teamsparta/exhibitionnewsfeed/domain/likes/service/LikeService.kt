package com.teamsparta.exhibitionnewsfeed.domain.likes.service

import com.teamsparta.exhibitionnewsfeed.domain.likes.dto.PostLikeRequest

interface LikeService {
    fun likePost(postId: Long, request: PostLikeRequest)
    fun removePostLike(likeId: Long, postId: Long)
}