package com.github.tobi812.sprykerplugin.services.resolver

import com.github.tobi812.sprykerplugin.services.renderer.dto.PhpClassInterface
import java.lang.Exception

interface ClassResolverInterface {
    @Throws(Exception::class)
    fun resolveBundleClass(classType: String, bundleName: String): PhpClassInterface?
    fun resolveClass(fullQualifiedName: String, findClassBelow: Boolean, findInterface: Boolean): PhpClassInterface?
    fun resolveClassBelow(fullQualifiedName: String): PhpClassInterface?
}
