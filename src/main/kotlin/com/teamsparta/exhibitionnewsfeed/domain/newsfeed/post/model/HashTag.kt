package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.dto.HashTagResponse
import jakarta.persistence.*

@Entity
@Table(name = "hashtag")
class HashTag(

    @Column(name = "tagName")
    var tagName: String,

    @OneToMany(mappedBy = "hashTag", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    val postTag: MutableList<PostTag> = mutableListOf(),
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun toResponse(): HashTagResponse {
        return HashTagResponse(
            id = this.id ?: throw IllegalStateException("User ID cannot be null"),
            tagName = this.tagName,
        )
    }
}