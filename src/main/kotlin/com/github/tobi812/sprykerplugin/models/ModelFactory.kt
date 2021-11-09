package com.github.tobi812.sprykerplugin.models

import com.github.tobi812.sprykerplugin.config.SprykerPluginConfig
import com.github.tobi812.sprykerplugin.models.command.AddMissingFactoryMethodsCommand
import com.github.tobi812.sprykerplugin.models.command.UpdateDocBlockCommand
import com.github.tobi812.sprykerplugin.models.definitions.DefinitionProvider
import com.github.tobi812.sprykerplugin.models.definitions.DefinitionProviderInterface
import com.github.tobi812.sprykerplugin.models.finder.ClassFinder
import com.github.tobi812.sprykerplugin.models.finder.ClassFinderInterface
import com.github.tobi812.sprykerplugin.models.finder.FactoryFinder
import com.github.tobi812.sprykerplugin.models.finder.MethodFinderInterface
import com.github.tobi812.sprykerplugin.models.generator.*
import com.github.tobi812.sprykerplugin.models.manager.ClassManager
import com.github.tobi812.sprykerplugin.models.manager.ClassManagerInterface
import com.github.tobi812.sprykerplugin.models.matcher.ClassTypeMatcher
import com.github.tobi812.sprykerplugin.models.matcher.ClassTypeMatcherInterface
import com.github.tobi812.sprykerplugin.models.renderer.PhpClassRenderer
import com.github.tobi812.sprykerplugin.models.renderer.PhpClassRendererInterface
import com.github.tobi812.sprykerplugin.models.resolver.ClassResolver
import com.github.tobi812.sprykerplugin.models.resolver.ClassResolverInterface
import com.github.tobi812.sprykerplugin.models.writer.FileWriter
import com.github.tobi812.sprykerplugin.models.writer.FileWriterInterface
import com.intellij.openapi.project.Project

class ModelFactory {
    val definitionProvider: DefinitionProviderInterface
    val classTypeMatcher: ClassTypeMatcherInterface

    init {
        this.definitionProvider = DefinitionProvider()
        this.classTypeMatcher = ClassTypeMatcher(this.definitionProvider)
    }

    fun createClassManager(project: Project, projectName: String): ClassManagerInterface {
        return ClassManager(
                this.createClassGenerator(project, projectName),
                this.createClassRenderer(),
                this.createFileWriter(project)
        )
    }

    fun createUpdateDocBlockCommand(project: Project, projectName: String): UpdateDocBlockCommand {
        return UpdateDocBlockCommand(
                this.classTypeMatcher,
                this.createDocBlockGenerator(project, projectName),
                this.createClassRenderer()
        )
    }

    fun createAddMissingFactoryMethodsCommand(): AddMissingFactoryMethodsCommand {
        return AddMissingFactoryMethodsCommand(this.createClassRenderer(), this.classTypeMatcher)
    }

    fun createFileWriter(project: Project): FileWriterInterface {
        return FileWriter(project)
    }

    fun createClassFinder(project: Project): ClassFinderInterface {
        return ClassFinder(project)
    }

    fun createMethodFinder(project: Project, projectName: String): MethodFinderInterface {
        return FactoryFinder(
            this.createClassGenerator(project, projectName),
            this.createClassFinder(project)
        )
    }

    private fun createClassGenerator(project: Project, projectName: String): ClassGeneratorInterface {
        return ClassGenerator(
            this.createParentGenerator(project, projectName),
            this.createDocBlockGenerator(project, projectName),
            this.definitionProvider
        )
    }

    private fun createClassResolver(project: Project, projectName: String): ClassResolverInterface {
        val config: SprykerPluginConfig = SprykerPluginConfig.getInstance(project)

        return ClassResolver(
            config.projectName,
            config.coreNames,
            this.createClassFinder(project),
            this.definitionProvider
        )
    }

    private fun createClassRenderer(): PhpClassRendererInterface {
        return PhpClassRenderer()
    }

    private fun createParentGenerator(project: Project, projectName: String): ParentGeneratorInterface {
        return ParentGenerator(
                this.createClassResolver(project, projectName),
                this.createClassFinder(project)
        )
    }

    private fun createDocBlockGenerator(project: Project, projectName: String): DocBlockGeneratorInterface {
        return DocBlockGenerator(
                this.createClassResolver(project, projectName),
                this.definitionProvider
        )
    }
}
