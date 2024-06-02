package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.repository

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.PostTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PostTagRepository : JpaRepository<PostTag, Long> {
    @Query("select pt from PostTag pt join HashTag h on pt.hashTag.id = h.id where h.tagName =?1 and pt.post.id = ?2")
    fun findIdByTagName(tagName: String, postId: Long?): PostTag?
}