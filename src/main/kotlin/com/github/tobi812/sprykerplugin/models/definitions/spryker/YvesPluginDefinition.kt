package com.github.tobi812.sprykerplugin.models.definitions.spryker

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface

class YvesPluginDefinition : ClassDefinitionInterface {
    override val classType: String
        get() = SprykerConstants.YVES_PLUGIN
    override val namePattern: String
        get() = "{{ className }}Plugin"
    override val namespacePattern: String
        get() = "{{ projectName }}\\Yves\\{{ bundleName }}\\Plugin"
    override val methodForReturnType: String
        get() = ""
    override val docBlockClasses: Array<String>
        get() = arrayOf(
                SprykerConstants.YVES_FACTORY,
                SprykerConstants.CLIENT)
    override val defaultParentClass: String
        get() = "Spryker\\Yves\\Kernel\\AbstractPlugin"
}