package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.controller

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CommentLikeRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.service.CommentLikeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/posts/{postId}/comments/{commentId}")
class CommentLikeController(
    private val commentLikeService: CommentLikeService

) {
    @PostMapping("like")
    fun addLike(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody commentLikeRequest: CommentLikeRequest
    ): ResponseEntity<Unit> {
        commentLikeService.addLike(postId, commentId, commentLikeRequest.userId)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("/like/{likeId}")
    fun removeLike(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @PathVariable likeId: Long,
        @RequestBody commentLikeRequest: CommentLikeRequest, // todo
        @RequestHeader("authorization") token: String
    ): ResponseEntity<Unit> {
        commentLikeService.removeLike(postId, commentId, commentLikeRequest.userId, likeId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}

