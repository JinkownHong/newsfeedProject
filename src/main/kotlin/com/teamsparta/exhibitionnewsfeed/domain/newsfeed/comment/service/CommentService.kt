package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.service

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CommentResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CreateCommentRequest

interface CommentService {
    fun createComment(postId: Long, createCommentRequest: CreateCommentRequest): CommentResponse
}