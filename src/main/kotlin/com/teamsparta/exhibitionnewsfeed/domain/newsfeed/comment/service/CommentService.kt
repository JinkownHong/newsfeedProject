package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.service

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CommentResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CreateCommentRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.UpdateCommentRequest

interface CommentService {
    fun createComment(postId: Long, createCommentRequest: CreateCommentRequest): CommentResponse

    fun updateComment(postId: Long, commentId: Long, updateCommentRequest: UpdateCommentRequest): CommentResponse

    fun deleteComment(postId: Long, commentId: Long): Unit
}