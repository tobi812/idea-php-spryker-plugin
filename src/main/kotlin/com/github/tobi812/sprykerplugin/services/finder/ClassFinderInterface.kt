package com.github.tobi812.sprykerplugin.services.finder

import com.github.tobi812.sprykerplugin.services.renderer.dto.PhpClassInterface
import com.jetbrains.php.lang.psi.elements.PhpClass

interface ClassFinderInterface {
    fun doesClassExist(fullQualifiedName: String): Boolean
    fun findClass(fullQualifiedCoreName: String): PhpClassInterface?
    fun findPhpClassCollection(fullQualifiedName: String): Collection<PhpClass?>
}
