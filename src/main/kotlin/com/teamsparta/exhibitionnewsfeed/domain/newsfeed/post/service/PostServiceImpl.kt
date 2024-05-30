package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.service

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.*
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.PostTag
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.repository.HashTagRepository
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.repository.PostRepository
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.repository.PostTagRepository
import com.teamsparta.exhibitionnewsfeed.domain.user.repository.UserRepository
import com.teamsparta.exhibitionnewsfeed.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val hashTagRepository: HashTagRepository,
    private val postTagRepository: PostTagRepository
) : PostService {

    override fun getPostById(postId: Long): PostResponse {
        val foundPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

        return PostResponse.from(foundPost)
    }

    override fun getAllPosts(): List<PostsResponse> {
        return postRepository.findAllByOrderByCreatedAtDesc().map { PostsResponse.from(it) }
    }

    override fun createPost(request: CreatePostRequest): PostsResponse {
        // TODO: 로그인 구현 후 nickname 가져오는 방식 수정
        val user = userRepository.findByIdOrNull(1L) ?: throw ModelNotFoundException("User", 1)

        var savePost = postRepository.save(request.toEntity(user))
        if (request.tagName.isNotBlank()) {
            val saveHashTag = hashTagRepository.save(request.toHashTagEntity())
            val postTag = postTagRepository.save<PostTag?>(
                PostTag(
                    post = savePost,
                    hashTag = saveHashTag
                )
            )
            savePost.postTag = mutableListOf(postTag)
        }
        return PostsResponse.from(savePost)
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