package com.github.tobi812.sprykerplugin.services.definitions

import java.lang.Exception
import java.util.HashMap

interface DefinitionProviderInterface {
    @Throws(Exception::class)
    fun getDefinitionByType(classType: String): ClassDefinitionInterface
    val allClassDefinitions: HashMap<String, ClassDefinitionInterface>
}
