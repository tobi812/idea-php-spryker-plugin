package com.github.tobi812.sprykerplugin.models.definitions.spryker

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface

class ZedQueryContainerDefinition : ClassDefinitionInterface {
    override val classType: String
        get() = SprykerConstants.ZED_QUERY_CONTAINER
    override val namePattern: String
        get() = "{{ bundleName }}QueryContainer"
    override val namespacePattern: String
        get() = "{{ projectName }}\\Zed\\{{ bundleName }}\\Persistence"
    override val methodForReturnType: String
        get() = "getQueryContainer()"
    override val docBlockClasses: Array<String>
        get() = arrayOf()
    override val defaultParentClass: String
        get() = "Spryker\\Zed\\Kernel\\Persistence\\AbstractQueryContainer"
}
