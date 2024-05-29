package com.teamsparta.exhibitionnewsfeed.exception

data class ComparativeVerificationException(
    val userId: Long
) : RuntimeException(
    "입력하신 userId $userId 가 일치하지 않습니다. Post 를 작성한 사용자만 수정할 수 있습니다."
)
