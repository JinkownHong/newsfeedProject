package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.service

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.model.CommentLike
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.repository.CommentLikeRepository
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.repository.CommentRepository
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.repository.PostRepository
import com.teamsparta.exhibitionnewsfeed.domain.user.repository.UserRepository
import com.teamsparta.exhibitionnewsfeed.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class CommentLikeServiceImpl(
    private val commentLikeRepository: CommentLikeRepository,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) : CommentLikeService {
    @Transactional
    override fun addLike(postId: Long, commentId: Long, userId: Long) {
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
    override fun removeLike(postId: Long, commentId: Long, userId: Long, likeId: Long) {
        validatePost(postId)

        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        val isExistLike = commentLikeRepository.existsByCommentAndUser(comment, user)
        if (isExistLike.not()) {
            throw IllegalStateException("Cannot remove a like when it has already")
        }
        commentLikeRepository.deleteById(likeId)
    }

    private fun validatePost(postId: Long) {
        postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
    }
}


