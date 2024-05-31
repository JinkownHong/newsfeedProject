package com.teamsparta.exhibitionnewsfeed.domain.likes.repository

import com.teamsparta.exhibitionnewsfeed.domain.likes.model.CommentLike
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.model.Comment
import com.teamsparta.exhibitionnewsfeed.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface CommentLikeRepository : JpaRepository<CommentLike, Long> {
    fun existsByCommentAndUser(comment: Comment, user: User): Boolean


    fun countByCommentId(commentId: Long): Long

}
