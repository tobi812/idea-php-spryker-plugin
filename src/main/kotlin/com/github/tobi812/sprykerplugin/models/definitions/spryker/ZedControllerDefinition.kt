package com.github.tobi812.sprykerplugin.models.definitions.spryker

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface

class ZedControllerDefinition : ClassDefinitionInterface {
    override val classType: String
        get() = SprykerConstants.ZED_CONTROLLER
    override val namePattern: String
        get() = "{{ className }}Controller"
    override val namespacePattern: String
        get() = "{{ projectName }}\\Zed\\{{ bundleName }}\\Communication\\Controller"
    override val methodForReturnType: String
        get() = ""
    override val docBlockClasses: Array<String>
        get() = arrayOf(
                SprykerConstants.ZED_COMMUNICATION_FACTORY,
                SprykerConstants.ZED_QUERY_CONTAINER,
                SprykerConstants.ZED_FACADE
        )
    override val defaultParentClass: String
        get() = "Spryker\\Zed\\Application\\Communication\\Controller\\AbstractController"
}