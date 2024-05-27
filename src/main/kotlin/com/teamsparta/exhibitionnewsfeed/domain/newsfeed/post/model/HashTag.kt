package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model

import com.teamsparta.exhibitionnewsfeed.domain.user.model.Users
import jakarta.persistence.*

@Entity
class HashTag (
    @Column(name = "tag-name")
    val tagName: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}