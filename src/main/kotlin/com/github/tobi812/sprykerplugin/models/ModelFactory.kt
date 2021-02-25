package com.github.tobi812.sprykerplugin.models

import com.intellij.openapi.project.Project

class ModelFactory {
    private val definitionProvider: DefinitionProviderInterface
    private var classResolver: ClassResolverInterface? = null

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

    private fun createClassResolver(project: Project, projectName: String): ClassResolverInterface? {
        if (classResolver == null) {
            classResolver = ClassResolver(
                    this.getConfig(project, projectName).getProjectName(),
                    this.getConfig(project, projectName).getCoreNames(),
                    this.createClassFinder(project),
                    this.definitionProvider
            )
        }
        return classResolver
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
        return PathResolver(getConfig(project, projectName).getBasePath())
    }

    private fun createFileWriter(project: Project): FileWriterInterface {
        return FileWriter(project)
    }

    private fun createDefinitionProvider(): DefinitionProviderInterface {
        return DefinitionProvider()
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