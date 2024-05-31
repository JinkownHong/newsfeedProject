package com.teamsparta.exhibitionnewsfeed.domain.likes.controller

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.RequestUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.TokenType
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
        checkAccessToken(authUser)
        if (authUser.id != commentLikeRequest.userId)
            throw UnauthorizedException("You are not allowed to use this comment like")
        likeService.likeComment(postId, commentId, commentLikeRequest.userId)
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
        checkAccessToken(authUser)
        if (authUser.id != commentLikeRequest.userId)
            throw UnauthorizedException("You are not allowed to use this comment like")
        likeService.removeCommentLike(postId, commentId, commentLikeRequest.userId, likeId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @GetMapping("/count")
    fun getLikesCount(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
    ) {
        return likeService.getLikesCount(postId, commentId)
    }

    private fun checkAccessToken(authUser: AuthUser) {
        if (authUser.tokenType != TokenType.ACCESS_TOKEN) throw UnauthorizedException("유효한 토큰이 아닙니다.")
    }
}

