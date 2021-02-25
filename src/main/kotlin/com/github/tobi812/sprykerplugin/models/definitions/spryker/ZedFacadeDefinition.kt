package com.github.tobi812.sprykerplugin.models.definitions.spryker

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface

class ZedFacadeDefinition : ClassDefinitionInterface {
    override val classType: String
        get() = SprykerConstants.ZED_FACADE
    override val namePattern: String
        get() = "{{ bundleName }}Facade"
    override val namespacePattern: String
        get() = "{{ projectName }}\\Zed\\{{ bundleName }}\\Business"
    override val methodForReturnType: String
        get() = "getFacade()"
    override val docBlockClasses: Array<String>
        get() = arrayOf(
                SprykerConstants.ZED_BUSINESS_FACTORY
        )
    override val defaultParentClass: String
        get() = "Spryker\\Zed\\Kernel\\Business\\AbstractFacade"
}