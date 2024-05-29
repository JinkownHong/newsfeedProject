package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.service

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CommentResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CreateCommentRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.UpdateCommentRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.model.Comment
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.repository.CommentRepository
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.repository.PostRepository
import com.teamsparta.exhibitionnewsfeed.domain.user.repository.UserRepository
import com.teamsparta.exhibitionnewsfeed.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class CommentServiceImpl(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
) : CommentService {

    @Transactional
    override fun createComment(postId: Long, createCommentRequest: CreateCommentRequest): CommentResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val user =
            userRepository.findByIdOrNull(createCommentRequest.userId) ?: throw ModelNotFoundException(
                "User",
                createCommentRequest.userId
            )

        val comment = Comment(
            content = createCommentRequest.content,
            user = user,
            post = post
        )

        return commentRepository.save(comment).toResponse()
    }

    @Transactional
    override fun updateComment(
        postId: Long,
        commentId: Long,
        updateCommentRequest: UpdateCommentRequest
    ): CommentResponse {
        postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        comment.content = updateCommentRequest.content

        return comment.toResponse()
    }

    @Transactional
    override fun deleteComment(postId: Long, commentId: Long) {
        postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        commentRepository.delete(comment)
    }

}





