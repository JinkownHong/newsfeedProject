package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.service

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.likes.repository.PostLikeRepository
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
    private val postTagRepository: PostTagRepository,
    private val postLikeRepository: PostLikeRepository,
) : PostService {

    override fun getPostById(postId: Long, authUser: AuthUser): PostResponse {
        val foundPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

        val like = postLikeRepository.findByPostIdAndUserId(foundPost.id!!, authUser.id)
        foundPost.heartStatus = like != null

        return PostResponse.from(foundPost)
    }

    override fun getAllPosts(authUser: AuthUser): List<PostsResponse> {
        val posts = postRepository.findAllByOrderByCreatedAtDesc()

        posts.forEach { post ->
            val like = postLikeRepository.findByPostIdAndUserId(post.id!!, authUser.id)
            post.heartStatus = like != null
        }
        return posts.map { PostsResponse.from(it) }
    }

    override fun getFilteredPosts(authUser: AuthUser, tagName: String): List<PostsResponse> {
        val post = postRepository.findById(tagName).map { PostsResponse.from(it) }

        return post.sortedByDescending { post -> post.createdAt }
    }

    @Transactional
    override fun createPost(authUser: AuthUser, request: CreatePostRequest): PostsResponse {
        val user = userRepository.findByIdOrNull(authUser.id) ?: throw ModelNotFoundException("User", authUser.id)
        val savePost = postRepository.save(request.toEntity(user))
        val tagName = request.tagName

        if (tagName.isNotBlank()) {
            val tagList = savePost.hashTagList(tagName)
            tagList.forEach { tag ->
                if (hashTagRepository.findHashTagByTagName(tag)?.tagName == tag) {
                    existHashTag(tag, savePost)
                } else {
                    createHashTag(tag, savePost)
                }
            }
        }
        return PostsResponse.from(savePost)
    }

    @Transactional
    override fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse {
        val foundPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val tagName = request.tagName

        foundPost.updatePostField(request)

        if (tagName.isNotBlank()) {
            val tagList = foundPost.hashTagList(tagName)
            tagList.forEach { tag ->
                if (hashTagRepository.findHashTagByTagName(tag)?.tagName != tag) createHashTag(tag, foundPost)
            }
        }
        return PostResponse.from(foundPost)
    }

    @Transactional
    override fun deletePost(postId: Long) {
        val foundPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        postRepository.delete(foundPost)
    }

    private fun existHashTag(tag: String, savePost: Post): Post {
        val saveHashTagId = hashTagRepository.findIdByTagName(tag)
        val savePostTag = postTagRepository.save<PostTag?>(
            PostTag(
                post = savePost,
                hashTag = saveHashTagId
            )
        )
        savePost.postTag.add(savePostTag)
        return savePost
    }

    private fun createHashTag(tag: String, savePost: Post): Post {
        val saveHashTag = hashTagRepository.save(toHashTagEntity(tag))
        val savePostTag = postTagRepository.save<PostTag?>(
            PostTag(
                post = savePost,
                hashTag = saveHashTag
            )
        )
        savePost.postTag.add(savePostTag)
        return savePost
    }
}