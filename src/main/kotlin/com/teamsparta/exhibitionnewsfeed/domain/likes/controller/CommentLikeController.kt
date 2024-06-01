package com.teamsparta.exhibitionnewsfeed.domain.likes.controller

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.RequestUser
import com.teamsparta.exhibitionnewsfeed.domain.likes.dto.CommentLikeRequest
import com.teamsparta.exhibitionnewsfeed.domain.likes.service.LikeService
import com.teamsparta.exhibitionnewsfeed.exception.UnauthorizedException
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/posts/{postId}/comments/{commentId}/likes")
class CommentLikeController(
    private val likeService: LikeService

) {
    @PostMapping()
    fun addLike(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
        @RequestBody commentLikeRequest: CommentLikeRequest
    ): ResponseEntity<Unit> {
        if (authUser.id != commentLikeRequest.userId)
            throw UnauthorizedException("You are not allowed to use this comment like")
        likeService.likeComment(postId, commentId, authUser, commentLikeRequest.userId)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("/{likeId}")
    fun removeLike(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @PathVariable likeId: Long,
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
        @RequestBody commentLikeRequest: CommentLikeRequest
    ): ResponseEntity<Unit> {
        if (authUser.id != commentLikeRequest.userId)
            throw UnauthorizedException("You are not allowed to use this comment like")
        likeService.removeCommentLike(postId, commentId, likeId, commentLikeRequest.userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}

