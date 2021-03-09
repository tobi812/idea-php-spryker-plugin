package com.github.tobi812.sprykerplugin.models.finder

import com.github.tobi812.sprykerplugin.models.renderer.dto.PhpClassInterface

interface ClassFinderInterface {
    fun doesClassExist(fullQualifiedName: String): Boolean
    fun findClass(fullQualifiedCoreName: String): PhpClassInterface?
}
