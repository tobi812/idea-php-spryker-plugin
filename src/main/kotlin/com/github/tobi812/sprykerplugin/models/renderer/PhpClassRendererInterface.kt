package com.github.tobi812.sprykerplugin.models.renderer

import com.github.tobi812.sprykerplugin.models.renderer.dto.DocBlockItem
import com.github.tobi812.sprykerplugin.models.renderer.dto.PhpClassInterface
import com.jetbrains.php.lang.psi.elements.PhpClass

interface PhpClassRendererInterface {
    fun renderPhpClass(phpClass: PhpClassInterface): String
    fun renderDocBlock(docBlockItems: List<DocBlockItem>): String
    fun renderFactoryMethod(phpClass: PhpClass): String
}
