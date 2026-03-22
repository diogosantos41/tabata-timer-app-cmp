package com.dscoding.tabatatimer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform