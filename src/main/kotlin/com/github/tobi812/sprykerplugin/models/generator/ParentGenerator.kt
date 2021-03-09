package com.github.tobi812.sprykerplugin.models.generator

import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface
import com.github.tobi812.sprykerplugin.models.finder.ClassFinderInterface
import com.github.tobi812.sprykerplugin.models.renderer.dto.PhpClassInterface
import com.github.tobi812.sprykerplugin.models.resolver.ClassResolverInterface

class ParentGenerator(
    private val classResolver: ClassResolverInterface,
    private val classFinder: ClassFinderInterface
) : ParentGeneratorInterface {

    override fun getParentClass(
        phpClass: PhpClassInterface,
        classDefinition: ClassDefinitionInterface
    ): PhpClassInterface? {
        var coreClass: PhpClassInterface? = this.classResolver.resolveClassBelow(phpClass.getFullQualifiedName())
        val defaultParentClass: String? = classDefinition.defaultParentClass
        if (coreClass == null && defaultParentClass != null) {
            coreClass = this.classFinder.findClass(defaultParentClass)
        }

        return coreClass
    }
}
