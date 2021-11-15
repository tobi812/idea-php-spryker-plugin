package com.github.tobi812.sprykerplugin.services.definitions.spryker

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.services.definitions.ClassDefinitionInterface

class ZedConfigDefinition : ClassDefinitionInterface {
    override val classType: String
        get() = SprykerConstants.ZED_CONFIG
    override val namePattern: String
        get() = "{{ bundleName }}Config"
    override val namespacePattern: String
        get() = "{{ projectName }}\\Zed\\{{ bundleName }}"
    override val methodForReturnType: String
        get() = "getConfig()"
    override val docBlockClasses: Array<String>
        get() = arrayOf()
    override val defaultParentClass: String
        get() = "Spryker\\Zed\\Kernel\\AbstractBundleConfig"
}
