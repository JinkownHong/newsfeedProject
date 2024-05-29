package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.service

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.*
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.PostsResponse.Companion.toResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.repository.PostRepository
import com.teamsparta.exhibitionnewsfeed.domain.user.repository.UserRepository
import com.teamsparta.exhibitionnewsfeed.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : PostService {
    override fun getPostById(postId: Long): PostResponse {
        val foundPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

        return PostResponse.from(foundPost)
    }

    override fun getAllPosts(): List<PostsResponse> {
        return postRepository.findAllByOrderByCreatedAtDesc().map { it.toResponse() }
    }

    override fun createPost(request: CreatePostRequest): PostsResponse {
        // TODO: 로그인 구현 후 nickname 가져오는 방식 수정
        val user = userRepository.findByIdOrNull(1L) ?: throw ModelNotFoundException("User", 1)

        return postRepository.save(request.toEntity(user)).toResponse()
    }

    override fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse {
        val foundPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        foundPost.updatePostField(request)

        return PostResponse.from(foundPost)
    }

    override fun deletePost(postId: Long) {
        val foundPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        postRepository.delete(foundPost)
    }
}