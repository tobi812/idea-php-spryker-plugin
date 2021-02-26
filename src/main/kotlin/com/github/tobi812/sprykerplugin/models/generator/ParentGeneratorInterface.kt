package com.github.tobi812.sprykerplugin.models.generator

import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface
import com.github.tobi812.sprykerplugin.models.renderer.dto.PhpClassInterface

interface ParentGeneratorInterface {
    fun getParentClass(
        phpClass: PhpClassInterface,
        classDefinition: ClassDefinitionInterface
    ): PhpClassInterface?
}