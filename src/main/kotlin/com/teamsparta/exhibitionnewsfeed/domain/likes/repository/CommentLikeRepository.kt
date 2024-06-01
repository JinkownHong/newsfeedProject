package com.teamsparta.exhibitionnewsfeed.domain.likes.repository

import com.teamsparta.exhibitionnewsfeed.domain.likes.model.CommentLike
import org.springframework.data.jpa.repository.JpaRepository

interface CommentLikeRepository : JpaRepository<CommentLike, Long> {
    fun existsByCommentIdAndUserId(commentId: Long, userId: Long): Boolean


    fun countByCommentId(commentId: Long): Long

}
