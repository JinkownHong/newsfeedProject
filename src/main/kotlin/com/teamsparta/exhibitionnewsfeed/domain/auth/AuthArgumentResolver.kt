package com.teamsparta.exhibitionnewsfeed.domain.auth

import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer


@Component
class AuthArgumentResolver(
    private val jwtTokenProvider: JwtTokenProvider
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(RequestUser::class.java) && parameter.parameterType.isAssignableFrom(
            AuthUser::class.java
        )
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): AuthUser {
        val request: HttpServletRequest =
            webRequest.getNativeRequest(HttpServletRequest::class.java) ?: throw IllegalArgumentException()
        val token = request.getHeader(HttpHeaders.AUTHORIZATION)?.replace("Bearer ", "")?.trim() ?: ""

        if (!jwtTokenProvider.validateToken(token)) throw IllegalArgumentException("Invalid Token")
        if (jwtTokenProvider.getSubject(token) != TokenType.ACCESS_TOKEN.name) throw IllegalArgumentException("ACCESS_TOKEN 아닙니다.")

        return AuthUser(id = jwtTokenProvider.getUserId(token), token = token)
    }
}