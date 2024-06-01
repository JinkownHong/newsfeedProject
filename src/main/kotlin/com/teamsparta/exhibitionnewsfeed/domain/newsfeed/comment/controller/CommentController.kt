package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.controller

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.RequestUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.TokenType
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CommentResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CreateCommentRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.UpdateCommentRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.service.CommentService
import com.teamsparta.exhibitionnewsfeed.exception.UnauthorizedException
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
        checkAccessToken(authUser)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(postId, authUser, request))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable postId: Long,
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
        @PathVariable commentId: Long,
        @RequestBody @Valid request: UpdateCommentRequest
    ): ResponseEntity<CommentResponse> {
        checkAccessToken(authUser)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(postId, commentId, request))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable postId: Long,
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
        @PathVariable commentId: Long
    ): ResponseEntity<Unit> {
        checkAccessToken(authUser)
        commentService.deleteComment(postId, commentId, authUser)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

    private fun checkAccessToken(authUser: AuthUser) {
        if (authUser.tokenType != TokenType.ACCESS_TOKEN) throw UnauthorizedException("유효한 토큰이 아닙니다.")
    }
}
