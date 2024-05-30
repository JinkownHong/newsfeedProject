package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model

import jakarta.persistence.*

@Entity
@Table(name = "post_tag")
class PostTag(
    @ManyToOne
    @JoinColumn(name = "post_id")
    val post: Post,

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    val hashTag: HashTag
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}