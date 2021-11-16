package com.github.tobi812.sprykerplugin.services.definitions.spryker

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.services.definitions.ClassDefinitionInterface

class YvesControllerDefinition : ClassDefinitionInterface {
    override val classType: String
        get() = SprykerConstants.YVES_CONTROLLER
    override val namePattern: String
        get() = "{{ className }}Controller"
    override val namespacePattern: String
        get() = "{{ projectName }}\\Yves\\{{ bundleName }}\\Controller"
    override val methodForReturnType: String
        get() = ""
    override val docBlockClasses: Array<String>
        get() = arrayOf(SprykerConstants.YVES_FACTORY, SprykerConstants.CLIENT)
    override val defaultParentClass: String
        get() = "Spryker\\Yves\\Application\\Controller\\AbstractController"
}
