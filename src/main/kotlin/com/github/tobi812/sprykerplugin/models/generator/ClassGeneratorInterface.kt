package com.github.tobi812.sprykerplugin.models.generator

import com.github.tobi812.sprykerplugin.actions.ClassConfig
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface
import com.github.tobi812.sprykerplugin.models.renderer.dto.PhpClassInterface
import java.lang.Exception

interface ClassGeneratorInterface {
    @Throws(Exception::class)
    fun generateClass(classType: String, config: ClassConfig): PhpClassInterface

    fun createFullQualifiedName(classDefinition: ClassDefinitionInterface, config: ClassConfig): String
}
