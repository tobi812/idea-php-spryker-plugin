package com.github.tobi812.sprykerplugin.services.definitions.spryker

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.services.definitions.ClassDefinitionInterface

class ClientFactoryDefinition : ClassDefinitionInterface {
    override val classType: String
        get() = SprykerConstants.CLIENT_FACTORY
    override val namePattern: String
        get() = "{{ bundleName }}Factory"
    override val namespacePattern: String
        get() = "{{ projectName }}\\Client\\{{ bundleName }}"
    override val methodForReturnType: String
        get() = "getFactory()"
    override val docBlockClasses: Array<String>
        get() = arrayOf()
    override val defaultParentClass: String
        get() = "Spryker\\Client\\Kernel\\AbstractFactory"
}
