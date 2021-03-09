package com.github.tobi812.sprykerplugin.models.definitions.spryker

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface

class ZedPluginDefinition : ClassDefinitionInterface {
    override val classType: String
        get() = SprykerConstants.ZED_PLUGIN
    override val namePattern: String
        get() = "{{ className }}Plugin"
    override val namespacePattern: String
        get() = "{{ projectName }}\\Zed\\{{ bundleName }}\\Communication\\Plugin"
    override val methodForReturnType: String
        get() = ""
    override val docBlockClasses: Array<String>
        get() = arrayOf(
                SprykerConstants.ZED_COMMUNICATION_FACTORY,
                SprykerConstants.ZED_FACADE)
    override val defaultParentClass: String
        get() = "Spryker\\Zed\\Kernel\\Communication\\AbstractPlugin"
}
