package com.teamsparta.exhibitionnewsfeed.config

import com.teamsparta.exhibitionnewsfeed.domain.auth.AuthArgumentResolver
import com.teamsparta.exhibitionnewsfeed.domain.auth.JwtTokenProvider
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val jwtTokenProvider: JwtTokenProvider
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(AuthArgumentResolver(jwtTokenProvider))
    }
}