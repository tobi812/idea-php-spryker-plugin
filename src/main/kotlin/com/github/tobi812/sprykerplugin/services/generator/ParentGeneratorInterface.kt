package com.github.tobi812.sprykerplugin.services.generator

import com.github.tobi812.sprykerplugin.services.definitions.ClassDefinitionInterface
import com.github.tobi812.sprykerplugin.services.renderer.dto.PhpClassInterface

interface ParentGeneratorInterface {
    fun getParentClass(
        phpClass: PhpClassInterface,
        classDefinition: ClassDefinitionInterface
    ): PhpClassInterface?
}
