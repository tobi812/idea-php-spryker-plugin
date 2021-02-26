package com.github.tobi812.sprykerplugin.models.renderer

import com.github.tobi812.sprykerplugin.models.renderer.dto.DocBlockItem
import com.github.tobi812.sprykerplugin.models.renderer.dto.PhpClassInterface

interface PhpClassRendererInterface {
    fun renderPhpClass(phpClass: PhpClassInterface): String
    fun renderDocBlock(docBlockItems: List<DocBlockItem>): String
}