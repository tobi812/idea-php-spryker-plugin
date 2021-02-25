package com.github.tobi812.sprykerplugin.models.definitions.spryker

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface

class ClientPluginDefinition : ClassDefinitionInterface {
    override val classType: String
        get() = SprykerConstants.CLIENT_PLUGIN
    override val namePattern: String
        get() = "{{ className }}Plugin"
    override val namespacePattern: String
        get() = "{{ projectName }}\\Client\\{{ bundleName }}\\Plugin"
    override val methodForReturnType: String
        get() = ""
    override val docBlockClasses: Array<String>
        get() = arrayOf(
                SprykerConstants.CLIENT,
                SprykerConstants.CLIENT_FACTORY)
    override val defaultParentClass: String
        get() = "Spryker\\Client\\Kernel\\AbstractPlugin"
}