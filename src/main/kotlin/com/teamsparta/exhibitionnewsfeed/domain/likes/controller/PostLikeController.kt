package com.teamsparta.exhibitionnewsfeed.domain.likes.controller

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.RequestUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.TokenType
import com.teamsparta.exhibitionnewsfeed.domain.likes.dto.PostLikeResponse
import com.teamsparta.exhibitionnewsfeed.domain.likes.service.LikeService
import com.teamsparta.exhibitionnewsfeed.exception.UnauthorizedException
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RequestMapping("/api/v1/likes")
@RestController
class PostLikeController(
    private val likeService: LikeService
) {
    @PostMapping("/posts/{postId}")
    fun likePost(
        @PathVariable postId: Long,
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
    ): ResponseEntity<Unit> {
        checkAuth(authUser)
        likeService.likePost(postId, authUser)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

    @DeleteMapping("/{likeId}/posts/{postId}")
    fun removePostLike(
        @PathVariable likeId: Long,
        @PathVariable postId: Long,
        @RequestUser @Parameter(hidden = true) authUser: AuthUser
    ):
            ResponseEntity<Unit> {
        checkAuth(authUser)
        likeService.removePostLike(likeId, postId)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

    @GetMapping("/posts/{postId}")
    fun getLikes(@PathVariable postId: Long): List<PostLikeResponse> {
        return likeService.getLikesByPostId(postId)
    }

    private fun checkAuth(authUser: AuthUser) {
        if (authUser.tokenType != TokenType.ACCESS_TOKEN) throw UnauthorizedException("유효한 토큰이 아닙니다.")
    }
}