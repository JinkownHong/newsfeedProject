package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model

import jakarta.persistence.*

@Entity
@Table(name = "hashtag")
class HashTag(

    @Column(name = "tagName")
    var tagName: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}