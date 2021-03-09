package com.github.tobi812.sprykerplugin.models.definitions.spryker

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface

class ZedPersistenceFactoryDefinition : ClassDefinitionInterface {
    override val classType: String
        get() = SprykerConstants.ZED_PERSISTENCE_FACTORY
    override val namePattern: String
        get() = "{{ bundleName }}PersistenceFactory"
    override val namespacePattern: String
        get() = "{{ projectName }}\\Zed\\{{ bundleName }}\\Persistence"
    override val methodForReturnType: String
        get() = "getFactory()"
    override val docBlockClasses: Array<String>
        get() = arrayOf(
                SprykerConstants.ZED_CONFIG
        )
    override val defaultParentClass: String
        get() = "Spryker\\Zed\\Kernel\\Persistence\\AbstractPersistenceFactory"
}
