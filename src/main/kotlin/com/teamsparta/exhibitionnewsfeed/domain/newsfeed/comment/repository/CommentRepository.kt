package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.repository

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long>
