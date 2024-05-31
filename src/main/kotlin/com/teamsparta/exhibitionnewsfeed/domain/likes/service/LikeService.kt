package com.teamsparta.exhibitionnewsfeed.domain.likes.service

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser

interface LikeService {
    fun likePost(postId: Long, authUser: AuthUser)
    fun removePostLike(likeId: Long, postId: Long)

    fun likeComment(postId: Long, commentId: Long, userId: Long)
    fun removeCommentLike(postId: Long, commentId: Long, userId: Long, likeId: Long)
}