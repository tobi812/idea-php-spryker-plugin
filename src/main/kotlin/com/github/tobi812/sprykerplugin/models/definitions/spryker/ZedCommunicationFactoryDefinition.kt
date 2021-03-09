package com.github.tobi812.sprykerplugin.models.definitions.spryker

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface

class ZedCommunicationFactoryDefinition : ClassDefinitionInterface {
    override val classType: String
        get() = SprykerConstants.ZED_COMMUNICATION_FACTORY
    override val namePattern: String
        get() = "{{ bundleName }}CommunicationFactory"
    override val namespacePattern: String
        get() = "{{ projectName }}\\Zed\\{{ bundleName }}\\Communication"
    override val methodForReturnType: String
        get() = ""
    override val docBlockClasses: Array<String>
        get() = arrayOf(
                SprykerConstants.ZED_QUERY_CONTAINER,
                SprykerConstants.ZED_CONFIG
        )
    override val defaultParentClass: String
        get() = "Spryker\\Zed\\Kernel\\Communication\\AbstractCommunicationFactory"
}
