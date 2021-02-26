package com.github.tobi812.sprykerplugin.models.renderer.dto

interface PhpFileInterface {
    val namespace: String
    val name: String
    fun getFullQualifiedName(): String
}