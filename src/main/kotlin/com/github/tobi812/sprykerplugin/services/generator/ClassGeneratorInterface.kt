package com.github.tobi812.sprykerplugin.services.generator

import com.github.tobi812.sprykerplugin.actions.ClassConfig
import com.github.tobi812.sprykerplugin.services.definitions.ClassDefinitionInterface
import com.github.tobi812.sprykerplugin.services.renderer.dto.PhpClassInterface
import java.lang.Exception

interface ClassGeneratorInterface {
    @Throws(Exception::class)
    fun generateClass(classType: String, config: ClassConfig): PhpClassInterface

    fun createFullQualifiedName(classDefinition: ClassDefinitionInterface, config: ClassConfig): String
}
