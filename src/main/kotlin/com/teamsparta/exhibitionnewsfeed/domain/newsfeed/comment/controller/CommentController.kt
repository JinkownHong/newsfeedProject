package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.controller

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.RequestUser
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CommentResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CreateCommentRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.UpdateCommentRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.service.CommentService
import io.swagger.v3.oas.annotations.Parameter
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
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
        @RequestBody request: CreateCommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(postId, authUser, request))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
        @RequestBody @Valid request: UpdateCommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(postId, commentId, authUser, request))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable postId: Long,
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
        @PathVariable commentId: Long
    ): ResponseEntity<Unit> {
        commentService.deleteComment(postId, commentId, authUser)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}
