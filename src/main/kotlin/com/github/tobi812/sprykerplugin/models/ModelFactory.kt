package com.github.tobi812.sprykerplugin.models

import com.github.tobi812.sprykerplugin.config.SprykerPluginConfig
import com.github.tobi812.sprykerplugin.models.command.UpdateDocBlockCommand
import com.github.tobi812.sprykerplugin.models.definitions.DefinitionProvider
import com.github.tobi812.sprykerplugin.models.definitions.DefinitionProviderInterface
import com.github.tobi812.sprykerplugin.models.finder.ClassFinder
import com.github.tobi812.sprykerplugin.models.finder.ClassFinderInterface
import com.github.tobi812.sprykerplugin.models.generator.*
import com.github.tobi812.sprykerplugin.models.manager.ClassManager
import com.github.tobi812.sprykerplugin.models.manager.ClassManagerInterface
import com.github.tobi812.sprykerplugin.models.matcher.ClassTypeMatcher
import com.github.tobi812.sprykerplugin.models.matcher.ClassTypeMatcherInterface
import com.github.tobi812.sprykerplugin.models.renderer.PhpClassRenderer
import com.github.tobi812.sprykerplugin.models.renderer.PhpClassRendererInterface
import com.github.tobi812.sprykerplugin.models.resolver.ClassResolver
import com.github.tobi812.sprykerplugin.models.resolver.ClassResolverInterface
import com.github.tobi812.sprykerplugin.models.resolver.PathResolver
import com.github.tobi812.sprykerplugin.models.resolver.PathResolverInterface
import com.github.tobi812.sprykerplugin.models.writer.FileWriter
import com.github.tobi812.sprykerplugin.models.writer.FileWriterInterface
import com.intellij.openapi.project.Project

class ModelFactory {
    private val definitionProvider: DefinitionProviderInterface

    init {
        this.definitionProvider = createDefinitionProvider()
    }

    fun createClassManager(project: Project, projectName: String): ClassManagerInterface {
        return ClassManager(
                this.createClassGenerator(project, projectName),
                this.createClassRenderer(),
                this.createPathResolver(project, projectName),
                this.createFileWriter(project)
        )
    }

    fun createClassTypeMatcher(): ClassTypeMatcherInterface {
        return ClassTypeMatcher(definitionProvider)
    }

    fun createUpdateDocBlockCommand(project: Project, projectName: String): UpdateDocBlockCommand {
        return UpdateDocBlockCommand(
                this.createClassTypeMatcher(),
                this.createDocBlockGenerator(project, projectName),
                this.createClassRenderer()
        )
    }

    fun createDefinitionProvider(): DefinitionProviderInterface {
        return DefinitionProvider()
    }

    private fun createClassResolver(project: Project, projectName: String): ClassResolverInterface {
        return ClassResolver(
            this.getConfig(project, projectName).projectName,
            this.getConfig(project, projectName).coreNames,
            this.createClassFinder(project),
            this.definitionProvider
        )
    }

    private fun createClassFinder(project: Project): ClassFinderInterface {
        return ClassFinder(project)
    }

    private fun createClassGenerator(project: Project, projectName: String): ClassGeneratorInterface {
        return ClassGenerator(
                this.createParentGenerator(project, projectName),
                this.createDocBlockGenerator(project, projectName),
                this.createDefinitionProvider()
        )
    }

    private fun createClassRenderer(): PhpClassRendererInterface {
        return PhpClassRenderer()
    }

    private fun createPathResolver(project: Project, projectName: String): PathResolverInterface {
        return PathResolver(getConfig(project, projectName).basePath)
    }

    private fun createFileWriter(project: Project): FileWriterInterface {
        return FileWriter(project)
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

    private fun getConfig(project: Project, projectName: String): SprykerPluginConfig {
        return SprykerPluginConfig.getInstance(project, projectName)
    }
}