package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTime {
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(nullable = true)
    var updatedAt: LocalDateTime? = null
}