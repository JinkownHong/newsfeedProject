package com.teamsparta.exhibitionnewsfeed.domain.likes.controller

import com.teamsparta.exhibitionnewsfeed.domain.likes.dto.CommentLikeRequest
import com.teamsparta.exhibitionnewsfeed.domain.likes.service.LikeService
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
        @RequestBody commentLikeRequest: CommentLikeRequest
    ): ResponseEntity<Unit> {
        likeService.likeComment(postId, commentId, commentLikeRequest.userId)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("/{likeId}")
    fun removeLike(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @PathVariable likeId: Long,
        @RequestBody commentLikeRequest: CommentLikeRequest, // todo
        @RequestHeader("authorization") token: String
    ): ResponseEntity<Unit> {
        likeService.removeCommentLike(postId, commentId, commentLikeRequest.userId, likeId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}

