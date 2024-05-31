package com.teamsparta.exhibitionnewsfeed.domain.likes.service

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.likes.dto.PostLikeResponse
import com.teamsparta.exhibitionnewsfeed.domain.likes.model.CommentLike
import com.teamsparta.exhibitionnewsfeed.domain.likes.model.PostLike
import com.teamsparta.exhibitionnewsfeed.domain.likes.repository.CommentLikeRepository
import com.teamsparta.exhibitionnewsfeed.domain.likes.repository.PostLikeRepository
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.repository.CommentRepository
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.repository.PostRepository
import com.teamsparta.exhibitionnewsfeed.domain.user.repository.UserRepository
import com.teamsparta.exhibitionnewsfeed.exception.ModelNotFoundException
import com.teamsparta.exhibitionnewsfeed.exception.UnauthorizedException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class LikeServiceImpl(
    private val commentLikeRepository: CommentLikeRepository,
    private val commentRepository: CommentRepository,
    private val postLikeRepository: PostLikeRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : LikeService {

    @Transactional
    override fun likePost(postId: Long, authUser: AuthUser) {
        val foundPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val foundUser =
            userRepository.findByIdOrNull(authUser.id) ?: throw ModelNotFoundException("User", authUser.id)

        if (foundPost.users.id == authUser.id) {
            throw IllegalArgumentException("You cannot like your own comment")
        }

        val alreadyLike = postLikeRepository.findByPostIdAndUserId(postId, authUser.id)
        if (alreadyLike != null) {
            throw IllegalStateException("Cannot add a like when it has already")
        }

        val like = PostLike(post = foundPost, user = foundUser)
        postLikeRepository.save(like)
    }

    @Transactional
    override fun removePostLike(likeId: Long, postId: Long) {
        postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

        val like = postLikeRepository.findByIdOrNull(likeId) ?: throw ModelNotFoundException("postLike", likeId)
        postLikeRepository.delete(like)
    }

    override fun getLikesByPostId(postId: Long): List<PostLikeResponse> {
        val likes = postLikeRepository.findByPostId(postId)
        return likes.map { like -> PostLikeResponse(nickname = like.user.nickname) }
    }

    @Transactional
    override fun likeComment(postId: Long, commentId: Long, userId: Long) {
        validatePost(postId)

        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)

        val isExistLike = commentLikeRepository.existsByCommentAndUser(comment, user)
        if (isExistLike) {
            throw IllegalStateException("Cannot add a like when it has already")
        }

        commentLikeRepository.save(
            CommentLike(
                comment = comment,
                user = user
            )
        )
    }

    @Transactional
    override fun removeCommentLike(postId: Long, commentId: Long, userId: Long, likeId: Long) {
        validatePost(postId)

        commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        val commentLike = commentLikeRepository.findByIdOrNull(likeId)
            ?: throw IllegalStateException("Cannot remove a like when it has already")
        if (userId != commentLike.user.id || commentId != commentLike.comment.id)
            throw UnauthorizedException("You cannot remove this comment")
        commentLikeRepository.deleteById(likeId)
    }

    private fun validatePost(postId: Long) {
        postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
    }
}