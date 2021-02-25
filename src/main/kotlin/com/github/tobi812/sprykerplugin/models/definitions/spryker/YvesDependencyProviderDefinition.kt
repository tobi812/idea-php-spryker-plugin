package com.github.tobi812.sprykerplugin.models.definitions.spryker

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface

class YvesDependencyProviderDefinition : ClassDefinitionInterface {
    override val classType: String
        get() = SprykerConstants.YVES_DEPENDENCY_PROVIDER
    override val namePattern: String
        get() = "{{ bundleName }}DependencyProvider"
    override val namespacePattern: String
        get() = "{{ projectName }}\\Yves\\{{ bundleName }}"
    override val methodForReturnType: String
        get() = ""
    override val docBlockClasses: Array<String>
        get() = arrayOf()
    override val defaultParentClass: String
        get() = "Spryker\\Yves\\Kernel\\AbstractBundleDependencyProvider"
}