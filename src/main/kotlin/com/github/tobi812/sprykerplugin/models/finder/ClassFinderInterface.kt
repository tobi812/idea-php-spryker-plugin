package com.github.tobi812.sprykerplugin.models.finder

import com.github.tobi812.sprykerplugin.models.renderer.dto.PhpClassInterface
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.elements.PhpClass
import java.util.ArrayList

interface ClassFinderInterface {
    fun doesClassExist(fullQualifiedName: String): Boolean
    fun findClass(fullQualifiedCoreName: String): PhpClassInterface?
    fun findPhpClassCollection(fullQualifiedName: String): Collection<PhpClass?>
}
