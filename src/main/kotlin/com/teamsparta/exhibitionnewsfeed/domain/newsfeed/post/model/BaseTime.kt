package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners
abstract class BaseTime {
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null

    @CreationTimestamp
    @Column(nullable = false)
    val updatedAt: LocalDateTime? = null
}