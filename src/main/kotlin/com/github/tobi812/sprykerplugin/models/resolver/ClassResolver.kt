package com.github.tobi812.sprykerplugin.models.resolver

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface
import com.github.tobi812.sprykerplugin.models.definitions.DefinitionProviderInterface
import com.github.tobi812.sprykerplugin.models.finder.ClassFinderInterface
import com.github.tobi812.sprykerplugin.models.renderer.dto.PhpClassInterface
import java.lang.Exception

class ClassResolver(
    private val projectName: String,
    private val projectHierarchy: Array<String>,
    private val classFinder: ClassFinderInterface,
    private val definitionProvider: DefinitionProviderInterface
) : ClassResolverInterface {

    @Throws(Exception::class)
    override fun resolveBundleClass(classType: String, bundleName: String): PhpClassInterface? {
        val classDefinition: ClassDefinitionInterface = definitionProvider.getDefinitionByType(classType)
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
        for (projectBelow in this.projectHierarchy) {
            if (this.projectName == projectBelow && findClassBelow) {
                continue
            }

            val fullQualifiedCoreName = fullQualifiedName.replaceFirst(projectName.toRegex(), projectBelow)
            if (findInterface) {
                val coreInterface: PhpClassInterface? = this.classFinder.findClass(fullQualifiedCoreName + "Interface")
                if (coreInterface != null) {
                    return coreInterface
                }
            }

            val coreClass: PhpClassInterface? = this.classFinder.findClass(fullQualifiedCoreName)
            if (coreClass != null) {
                return coreClass
            }
        }

        return null
    }
}