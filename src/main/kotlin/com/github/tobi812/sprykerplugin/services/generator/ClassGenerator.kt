package com.github.tobi812.sprykerplugin.services.generator

import com.github.tobi812.sprykerplugin.actions.ClassConfig
import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.services.definitions.ClassDefinitionInterface
import com.github.tobi812.sprykerplugin.services.definitions.DefinitionProviderInterface
import com.github.tobi812.sprykerplugin.services.renderer.dto.DocBlockItem
import com.github.tobi812.sprykerplugin.services.renderer.dto.PhpClassInterface
import com.github.tobi812.sprykerplugin.services.renderer.dto.PhpClassItem
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.lang.Exception

class ClassGenerator(
    private val project: Project
) : ClassGeneratorInterface {

    @Throws(Exception::class)
    override fun generateClass(classType: String, config: ClassConfig): PhpClassInterface {
        val classDefinition: ClassDefinitionInterface = project.service<DefinitionProviderInterface>().getDefinitionByType(classType)
        val namespace = this.createNamespace(classDefinition, config)
        val className = this.createClassName(classDefinition, config)
        val phpClass: PhpClassInterface = PhpClassItem(namespace, className)

        this.addParentClass(phpClass, classDefinition)
        this.addDocBlock(phpClass, classDefinition, config)

        return phpClass
    }

    override fun createFullQualifiedName(classDefinition: ClassDefinitionInterface, config: ClassConfig): String {
        val namespace: String = this.createNamespace(classDefinition, config)
        val className: String = this.createClassName(classDefinition, config)

        return namespace + "\\" + className
    }

    private fun createNamespace(classDefinition: ClassDefinitionInterface, config: ClassConfig): String {
        val projectName = config.projectName
        val moduleName = config.moduleName

        return classDefinition.namespacePattern
            .replace(SprykerConstants.PROJECT_NAME_PLACEHOLDER, projectName)
            .replace(SprykerConstants.MODULE_NAME_PLACEHOLDER, moduleName)
    }

    private fun createClassName(classDefinition: ClassDefinitionInterface, config: ClassConfig): String {
        val bundleName: String = config.moduleName
        val className: String = config.name ?: "Index"

        return classDefinition.namePattern
            .replace(SprykerConstants.CLASS_NAME_PLACEHOLDER, className)
            .replace(SprykerConstants.MODULE_NAME_PLACEHOLDER, bundleName)
    }

    private fun addParentClass(phpClass: PhpClassInterface, classDefinition: ClassDefinitionInterface) {
        val parentGenerator = project.service<ParentGeneratorInterface>()
        val parentClass: PhpClassInterface? = parentGenerator.getParentClass(phpClass, classDefinition)
        if (parentClass != null) {
            phpClass.setParentClass(parentClass)
        }
    }

    @Throws(Exception::class)
    private fun addDocBlock(
        phpClass: PhpClassInterface,
        classDefinition: ClassDefinitionInterface,
        config: ClassConfig
    ) {
        val bundleName: String = config.moduleName
        val docBlockGenerator = project.service<DocBlockGeneratorInterface>()
        val docBlockClassTypes: Array<String> = classDefinition.docBlockClasses
        val docBlockItems: List<DocBlockItem> = docBlockGenerator.getDocBlockItems(docBlockClassTypes, bundleName)

        for (docBlockItem in docBlockItems) {
            phpClass.addDocBlockItem(docBlockItem)
        }
    }
}
