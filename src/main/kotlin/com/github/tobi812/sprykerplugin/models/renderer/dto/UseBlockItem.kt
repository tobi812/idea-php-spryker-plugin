package com.github.tobi812.sprykerplugin.models.renderer.dto

import com.intellij.openapi.util.text.StringUtil

class UseBlockItem(private val className: String, private val namespace: String) {
    var alias: String? = null
    val fullQualifiedClassName: String
        get() = StringUtil.trimEnd(namespace, "\\") + "\\" + className
}