package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.controller

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CommentResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CreateCommentRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.service.CommentService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/posts/{postId}/comments")
@RestController
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping()
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody @Valid createCommentRequest: CreateCommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(postId, createCommentRequest))
    }
}
