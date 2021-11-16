package com.github.tobi812.sprykerplugin.services.generator

import com.github.tobi812.sprykerplugin.services.definitions.ClassDefinitionInterface
import com.github.tobi812.sprykerplugin.services.finder.ClassFinderInterface
import com.github.tobi812.sprykerplugin.services.renderer.dto.PhpClassInterface
import com.github.tobi812.sprykerplugin.services.resolver.ClassResolverInterface
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

class ParentGenerator(
    private val project: Project
) : ParentGeneratorInterface {

    override fun getParentClass(
        phpClass: PhpClassInterface,
        classDefinition: ClassDefinitionInterface
    ): PhpClassInterface? {
        val classResolver = project.service<ClassResolverInterface>()
        var coreClass: PhpClassInterface? = classResolver.resolveClassBelow(phpClass.getFullQualifiedName())
        val defaultParentClass: String? = classDefinition.defaultParentClass
        if (coreClass == null && defaultParentClass != null) {
            coreClass = project.service<ClassFinderInterface>().findClass(defaultParentClass)
        }

        return coreClass
    }
}
