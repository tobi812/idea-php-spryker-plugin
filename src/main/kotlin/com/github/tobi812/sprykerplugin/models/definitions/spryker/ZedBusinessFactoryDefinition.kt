package com.github.tobi812.sprykerplugin.models.definitions.spryker

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface

class ZedBusinessFactoryDefinition : ClassDefinitionInterface {
    override val classType: String
        get() = SprykerConstants.ZED_BUSINESS_FACTORY
    override val namePattern: String
        get() = "{{ bundleName }}BusinessFactory"
    override val namespacePattern: String
        get() = "{{ projectName }}\\Zed\\{{ bundleName }}\\Business"
    override val methodForReturnType: String
        get() = "getFactory()"
    override val docBlockClasses: Array<String>
        get() = arrayOf(
                SprykerConstants.ZED_CONFIG,
                SprykerConstants.ZED_QUERY_CONTAINER
        )
    override val defaultParentClass: String
        get() = "Spryker\\Zed\\Kernel\\Business\\AbstractBusinessFactory"
}