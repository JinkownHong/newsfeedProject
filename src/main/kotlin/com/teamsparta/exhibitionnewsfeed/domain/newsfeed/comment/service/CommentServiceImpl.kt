package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.service

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthUser
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CommentResponse
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.CreateCommentRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.dto.UpdateCommentRequest
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.model.Comment
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.repository.CommentRepository
import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.repository.PostRepository
import com.teamsparta.exhibitionnewsfeed.domain.user.repository.UserRepository
import com.teamsparta.exhibitionnewsfeed.exception.ModelNotFoundException
import com.teamsparta.exhibitionnewsfeed.exception.UnauthorizedException
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
    override fun createComment(postId: Long, authUser: AuthUser, request: CreateCommentRequest): CommentResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val user =
            userRepository.findByIdOrNull(authUser.id) ?: throw ModelNotFoundException(
                "User",
                authUser.id
            )

        val comment = Comment(
            content = request.content,
            user = user,
            post = post

        )
        return commentRepository.save(comment).toResponse()
    }

    @Transactional
    override fun updateComment(
        postId: Long,
        commentId: Long,
        authUser: AuthUser,
        request: UpdateCommentRequest
    ): CommentResponse {
        postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (comment.user.id != authUser.id)
            throw UnauthorizedException("권한이 없습니다.")

        return comment.toResponse()
    }

    @Transactional
    override fun deleteComment(postId: Long, commentId: Long, authUser: AuthUser) {
        postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (comment.user.id != authUser.id)
            throw UnauthorizedException("권한이 없습니다.")

        commentRepository.delete(comment)
    }
}





