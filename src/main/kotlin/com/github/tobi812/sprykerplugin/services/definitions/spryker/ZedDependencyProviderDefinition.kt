package com.github.tobi812.sprykerplugin.services.definitions.spryker

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.services.definitions.ClassDefinitionInterface

class ZedDependencyProviderDefinition : ClassDefinitionInterface {
    override val classType: String
        get() = SprykerConstants.ZED_DEPENDENCY_PROVIDER
    override val namePattern: String
        get() = "{{ bundleName }}DependencyProvider"
    override val namespacePattern: String
        get() = "{{ projectName }}\\Zed\\{{ bundleName }}"
    override val methodForReturnType: String
        get() = ""
    override val docBlockClasses: Array<String>
        get() = arrayOf()
    override val defaultParentClass: String
        get() = "Spryker\\Zed\\Kernel\\AbstractBundleDependencyProvider"
}
