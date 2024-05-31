package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.controller

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.CreatePostRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.PostResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.PostsResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.UpdatePostRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.service.PostService
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
    fun createPost(@Valid @RequestBody request: CreatePostRequest): ResponseEntity<PostsResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.createPost(request))
    }

    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @Valid @RequestBody request: UpdatePostRequest
    ): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.updatePost(postId, request))
    }

    @DeleteMapping("/{postId}")
    fun deletePost(@PathVariable postId: Long): ResponseEntity<Unit> {
        postService.deletePost(postId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}

