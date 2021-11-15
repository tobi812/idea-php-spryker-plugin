package com.github.tobi812.sprykerplugin.services.resolver

import com.github.tobi812.sprykerplugin.config.SprykerPluginConfig
import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.services.definitions.ClassDefinitionInterface
import com.github.tobi812.sprykerplugin.services.definitions.DefinitionProviderInterface
import com.github.tobi812.sprykerplugin.services.finder.ClassFinderInterface
import com.github.tobi812.sprykerplugin.services.renderer.dto.PhpClassInterface
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.lang.Exception

class ClassResolver(
    private val project: Project,
) : ClassResolverInterface {

    @Throws(Exception::class)
    override fun resolveBundleClass(classType: String, bundleName: String): PhpClassInterface? {
        val projectName = project.service<SprykerPluginConfig>().projectName
        val classDefinition: ClassDefinitionInterface = project.service<DefinitionProviderInterface>().getDefinitionByType(classType)
        val classPattern: String =
            classDefinition.namespacePattern + "\\" + classDefinition.namePattern
        val fullQualifiedName: String = classPattern
            .replace(SprykerConstants.PROJECT_NAME_PLACEHOLDER, projectName)
            .replace(SprykerConstants.MODULE_NAME_PLACEHOLDER, bundleName)

        return resolveClass(fullQualifiedName, findClassBelow = false, findInterface = true)
    }

    override fun resolveClassBelow(fullQualifiedName: String): PhpClassInterface? {
        return this.resolveClass(fullQualifiedName, findClassBelow = true, findInterface = false)
    }

    override fun resolveClass(
        fullQualifiedName: String,
        findClassBelow: Boolean,
        findInterface: Boolean
    ): PhpClassInterface? {
        val config = project.service<SprykerPluginConfig>()
        val projectName = config.projectName
        val classFinder = project.service<ClassFinderInterface>()

        for (projectBelow in config.coreNames) {
            if (projectName == projectBelow && findClassBelow) {
                continue
            }

            val fullQualifiedCoreName = fullQualifiedName.replaceFirst(projectName.toRegex(), projectBelow)
            if (findInterface) {
                val coreInterface: PhpClassInterface? = classFinder.findClass(fullQualifiedCoreName + "Interface")
                if (coreInterface != null) {
                    return coreInterface
                }
            }

            val coreClass: PhpClassInterface? = classFinder.findClass(fullQualifiedCoreName)
            if (coreClass != null) {
                return coreClass
            }
        }

        return null
    }
}
