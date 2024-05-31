package com.teamsparta.exhibitionnewsfeed.domain.likes.service

import com.teamsparta.exhibitionnewsfeed.domain.likes.dto.PostLikeRequest

interface LikeService {
    fun likePost(postId: Long, request: PostLikeRequest)
    fun removePostLike(likeId: Long, postId: Long)

    fun likeComment(postId: Long, commentId: Long, userId: Long)
    fun removeCommentLike(postId: Long, commentId: Long, userId: Long, likeId: Long)
}