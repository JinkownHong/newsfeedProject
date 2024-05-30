package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.service

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.*
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.Post
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.PostTag
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.repository.HashTagRepository
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.repository.PostRepository
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.repository.PostTagRepository
import com.teamsparta.exhibitionnewsfeed.domain.user.repository.UserRepository
import com.teamsparta.exhibitionnewsfeed.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    @Transactional
    override fun createPost(request: CreatePostRequest): PostsResponse {
        // TODO: 로그인 구현 후 nickname 가져오는 방식 수정
        val user = userRepository.findByIdOrNull(1L) ?: throw ModelNotFoundException("User", 1)
        val tagName = request.tagName

        var savePost = postRepository.save(request.toEntity(user))

        saveHashTags(tagName, savePost)

        return PostsResponse.from(savePost)
    }

    @Transactional
    override fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse {
        val foundPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        foundPost.updatePostField(request)

        return PostResponse.from(foundPost)
    }

    @Transactional
    override fun deletePost(postId: Long) {
        val foundPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        postRepository.delete(foundPost)
    }

    private fun saveHashTags(tagName: String, savePost: Post): Post {
        if (tagName.isNotBlank()) {
            val tagList = savePost.hashTagList(tagName)
            tagList.forEach { tag ->
                if (hashTagRepository.findHashTagByTagName(tag)?.tagName == tag) {
                    val saveHashTagId = hashTagRepository.findIdByTagName(tag)
                    val savePostTag = postTagRepository.save<PostTag?>(
                        PostTag(
                            post = savePost,
                            hashTag = saveHashTagId
                        )
                    )
                    savePost.postTag.add(savePostTag)
                } else {
                    val saveHashTag = hashTagRepository.save(toHashTagEntity(tag))
                    val savePostTag = postTagRepository.save<PostTag?>(
                        PostTag(
                            post = savePost,
                            hashTag = saveHashTag
                        )
                    )
                    savePost.postTag.add(savePostTag)
                }
            }
        }
        return savePost
    }
}