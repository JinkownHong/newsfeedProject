package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.controller

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.auth.RequestUser
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.CreatePostRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.PostResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.PostsResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.UpdatePostRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.service.PostService
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
    fun getPost(
        @PathVariable postId: Long,
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
    ): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.getPostById(postId, authUser))
    }

    @GetMapping
    fun getAllPosts(
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
        @RequestParam(required = false) tagName: String?
    ): ResponseEntity<Any> {
        val body = if (tagName != null) {
            postService.getFilteredPosts(authUser, tagName)
        } else {
            postService.getAllPosts(authUser)
        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(body)
    }

    @PostMapping
    fun createPost(
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
        @Valid @RequestBody request: CreatePostRequest
    ): ResponseEntity<PostsResponse> {
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
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(postId, authUser, request))
    }

    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable postId: Long,
        @RequestUser @Parameter(hidden = true) authUser: AuthUser,
    ): ResponseEntity<Unit> {
        postService.deletePost(postId, authUser)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}

