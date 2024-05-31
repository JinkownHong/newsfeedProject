package com.teamsparta.exhibitionnewsfeed.domain.likes.controller

import com.teamsparta.exhibitionnewsfeed.domain.likes.dto.PostLikeRequest
import com.teamsparta.exhibitionnewsfeed.domain.likes.service.LikeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RequestMapping("/api/v1/likes")
@RestController
class PostLikeController(
    private val likeService: LikeService
) {

    @PostMapping("/posts/{postId}")
    fun likePost(@PathVariable postId: Long, @RequestBody request: PostLikeRequest): ResponseEntity<Unit> {
        likeService.likePost(postId, request)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

    @DeleteMapping("/{likeId}/posts/{postId}")
    fun removePostLike(
        @PathVariable likeId: Long,
        @PathVariable postId: Long
    ):
            ResponseEntity<Unit> {
        likeService.removePostLike(likeId, postId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}