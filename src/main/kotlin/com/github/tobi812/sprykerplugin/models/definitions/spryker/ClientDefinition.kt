package com.github.tobi812.sprykerplugin.models.definitions.spryker

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface

class ClientDefinition : ClassDefinitionInterface {
    override val classType: String
        get() = SprykerConstants.CLIENT
    override val namePattern: String
        get() = "{{ bundleName }}Client"
    override val namespacePattern: String
        get() = "{{ projectName }}\\Client\\{{ bundleName }}"
    override val methodForReturnType: String
        get() = "getClient()"
    override val docBlockClasses: Array<String>
        get() = arrayOf()
    override val defaultParentClass: String?
        get() = null
}