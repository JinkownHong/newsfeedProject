package com.teamsparta.exhibitionnewsfeed.domain.user.model

import com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model.Post
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
class Users (
    @Column(name = "email")
    val email: String,

    @Column(name = "password")
    val password : String,

    @Column(name = "nickname")
    var nickname : String,

    @Column(name = "description")
    var description : String,
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}