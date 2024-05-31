package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.service

interface CommentLikeService {
    fun addLike(postId: Long, commentId: Long, userId: Long)

    fun removeLike(postId: Long, commentId: Long, userId: Long, likeId: Long)
}