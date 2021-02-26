package com.github.tobi812.sprykerplugin.models.resolver

interface PathResolverInterface {
    fun resolvePath(namespace: String): String
}