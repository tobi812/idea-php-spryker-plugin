package com.github.tobi812.sprykerplugin.models.resolver

class PathResolver(private val basePath: String) : PathResolverInterface {
    override fun resolvePath(namespace: String): String {
        val relativePath = namespace.replace("\\", "/")
        return "$basePath/$relativePath"
    }
}