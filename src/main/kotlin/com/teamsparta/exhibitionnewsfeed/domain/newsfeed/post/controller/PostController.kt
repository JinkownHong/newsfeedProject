package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.controller

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.RequestUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.TokenType
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.CreatePostRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.PostResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.PostsResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.UpdatePostRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.service.PostService
import com.teamsparta.exhibitionnewsfeed.exception.UnauthorizedException
import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/posts")
@RestController
class PostController(
    private val postService: PostService
) {
    @GetMapping("/{postId}")
    fun getPost(@PathVariable postId: Long): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostById(postId))
    }

    @GetMapping
    fun getAllPosts(): ResponseEntity<List<PostsResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getAllPosts())
    }

    @PostMapping
    fun createPost(
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
        @Valid @RequestBody request: CreatePostRequest
    ): ResponseEntity<PostsResponse> {
        checkAuth(authUser)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.createPost(authUser, request))
    }

    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
        @Valid @RequestBody request: UpdatePostRequest
    ): ResponseEntity<PostResponse> {
        checkAuth(authUser)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(postId, request))
    }

    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable postId: Long,
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
    ): ResponseEntity<Unit> {
        checkAuth(authUser)

        postService.deletePost(postId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

    private fun checkAuth(authUser: AuthUser) {
        if (authUser.tokenType != TokenType.ACCESS_TOKEN) throw UnauthorizedException("유효한 토큰이 아닙니다.")
    }
}

