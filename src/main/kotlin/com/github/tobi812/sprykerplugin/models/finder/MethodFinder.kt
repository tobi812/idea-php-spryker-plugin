package com.github.tobi812.sprykerplugin.models.finder

import com.github.tobi812.sprykerplugin.actions.ClassConfig
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface
import com.github.tobi812.sprykerplugin.models.definitions.spryker.*
import com.github.tobi812.sprykerplugin.models.generator.ClassGeneratorInterface
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.elements.PhpClass
import java.util.ArrayList

class MethodFinder(
    private val classGenerator: ClassGeneratorInterface,
    private val classFinder: ClassFinderInterface) : MethodFinderInterface {

    override fun findClassFactoryMethod(phpClass: PhpClass): ArrayList<Method>? {
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
        val fqFactoryName = this.classGenerator
            .createFullQualifiedName(classDefinition, config)
        val phpClassCollection = this.classFinder.findPhpClassCollection(fqFactoryName)

        val methodCollection: ArrayList<Method> = ArrayList()
        if (phpClassCollection.isNotEmpty()) {
            val factoryClass = phpClassCollection.iterator().next() ?: return null

            for (method in factoryClass.methods) {
                if (method.name == "create" + phpClass.name) {
                    methodCollection.add(method)
                    return methodCollection
                }
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