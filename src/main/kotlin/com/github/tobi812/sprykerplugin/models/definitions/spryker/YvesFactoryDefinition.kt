package com.github.tobi812.sprykerplugin.models.definitions.spryker

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface

class YvesFactoryDefinition : ClassDefinitionInterface {
    override val classType: String
        get() = SprykerConstants.YVES_FACTORY
    override val namePattern: String
        get() = "{{ bundleName }}Factory"
    override val namespacePattern: String
        get() = "{{ projectName }}\\Yves\\{{ bundleName }}"
    override val methodForReturnType: String
        get() = "getFactory()"
    override val docBlockClasses: Array<String>
        get() = arrayOf()
    override val defaultParentClass: String
        get() = "Spryker\\Yves\\Kernel\\AbstractFactory"
}
