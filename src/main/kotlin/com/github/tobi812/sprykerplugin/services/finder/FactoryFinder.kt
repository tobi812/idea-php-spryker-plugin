package com.github.tobi812.sprykerplugin.services.finder

import com.github.tobi812.sprykerplugin.actions.ClassConfig
import com.github.tobi812.sprykerplugin.services.definitions.ClassDefinitionInterface
import com.github.tobi812.sprykerplugin.services.definitions.spryker.*
import com.github.tobi812.sprykerplugin.services.generator.ClassGeneratorInterface
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.elements.PhpClass

class FactoryFinder(
    private val project: Project
): FactoryFinderInterface {

    override fun findClassFactory(phpClass: PhpClass): PhpClass? {
        val fqClassName = phpClass.fqn
        val classSegments = fqClassName.split("\\\\".toRegex()).toTypedArray()

        val projectName = classSegments[1]
        val appName = classSegments[2]
        val moduleName = classSegments[3]

        val classDefinition = when (appName) {
            "Zed" -> this.findZedFactoryDefinition(classSegments)
            "Yves" -> YvesFactoryDefinition()
            "Client" -> ClientFactoryDefinition()
            else -> null
        } ?: return null

        val config = ClassConfig(moduleName, projectName)
        val classGenerator = project.service<ClassGeneratorInterface>()
        val fqFactoryName = classGenerator.createFullQualifiedName(classDefinition, config)

        val phpClassCollection = project.service<ClassFinderInterface>().findPhpClassCollection(fqFactoryName)

        return phpClassCollection.iterator().next()
    }

    override fun findClassFactoryMethod(phpClass: PhpClass, factory: PhpClass): Method? {
        for (method in factory.methods) {
            if (method.name == "create" + phpClass.name) {
                return method
            }
        }

        return null
    }

    private fun findZedFactoryDefinition(classSegments: Array<String>): ClassDefinitionInterface? {
        val classDefinition = when (classSegments[4]) {
            "Communication" -> ZedCommunicationFactoryDefinition()
            "Business" -> ZedBusinessFactoryDefinition()
            "Persistence" -> ZedPersistenceFactoryDefinition()
            else -> null
        }

        return classDefinition
    }
}