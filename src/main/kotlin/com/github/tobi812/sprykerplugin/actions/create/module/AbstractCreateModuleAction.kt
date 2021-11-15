package com.github.tobi812.sprykerplugin.actions.create.module

import com.github.tobi812.sprykerplugin.actions.ClassConfig
import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.services.definitions.ClassDefinitionInterface
import com.github.tobi812.sprykerplugin.services.definitions.DefinitionProviderInterface
import com.github.tobi812.sprykerplugin.services.manager.ClassManagerInterface
import com.github.tobi812.sprykerplugin.services.writer.FileWriterInterface
import com.intellij.ide.IdeView
import com.intellij.ide.actions.CreateElementActionBase
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import java.lang.Exception
import java.util.*
import java.util.function.Consumer
import javax.swing.Icon

@Suppress("NAME_SHADOWING")
abstract class AbstractCreateModuleAction protected constructor(text: String?, description: String?, icon: Icon?) :
    CreateElementActionBase(text, description, icon) {
    private var project: Project? = null
    protected abstract val actionName: String
    abstract val applicationName: String
    abstract val classTypes: ArrayList<String>
    abstract override fun getErrorTitle(): String

    override fun invokeDialog(
        project: Project,
        directory: PsiDirectory,
        elementsConsumer: Consumer<Array<PsiElement?>>
    ) {
        this.project = project
        val validator = MyInputValidator(project, directory)
        val moduleName = Messages.showInputDialog(
            "Set module name",
            "Input module name",
            Messages.getQuestionIcon(),
            "",
            validator
        )

        if (moduleName == "" || moduleName == null) {
            return
        }

        elementsConsumer.accept(validator.createdElements)
    }

    @Throws(Exception::class)
    override fun create(newName: String, directory: PsiDirectory): Array<PsiElement> {
        val moduleName: String = newName
        if (directory.findSubdirectory(moduleName) != null) {
            Messages.showErrorDialog(this.getProject(), "Module $moduleName already exists!", "Error")

            return PsiElement.EMPTY_ARRAY
        }

        val moduleDirectory: PsiDirectory = this.createSubdirectory(directory, moduleName)
        val projectName = this.matchProjectName(directory)
        val classConfig = ClassConfig(moduleName, projectName)

        return this.createModuleClasses(this.getProject(), moduleDirectory, classConfig)
    }

    @Throws(Exception::class)
    private fun matchProjectName(psiDirectory: PsiDirectory): String {
        val projectDir: PsiDirectory = psiDirectory.parent ?: throw Exception()

        return projectDir.name
    }

    @Throws(Exception::class)
    protected fun createModuleClasses(
        project: Project,
        moduleDirectory: PsiDirectory,
        classConfig: ClassConfig
    ): Array<PsiElement> {
        val classManager = this.getProject().service<ClassManagerInterface>()
        val definitionProvider = project.service<DefinitionProviderInterface>()
        val createdElements: ArrayList<PsiElement> = ArrayList<PsiElement>()

        for (classType in this.classTypes) {
            val classDefinition: ClassDefinitionInterface = definitionProvider.getDefinitionByType(classType)
            val classDirectory: PsiDirectory = this.resolveClassDirectory(classDefinition, moduleDirectory)

            createdElements.add(classManager.handleClass(classDirectory, classType, classConfig))
        }

        return createdElements.toTypedArray()
    }

    @Throws(Exception::class)
    protected fun resolveClassDirectory(
        classDirectory: ClassDefinitionInterface,
        moduleDirectory: PsiDirectory
    ): PsiDirectory {
        var namespacePattern: String = classDirectory.namespacePattern
        namespacePattern = namespacePattern.replace(SprykerConstants.MODULE_NAME_PLACEHOLDER, "moduleName")
        val namespaceSplit = namespacePattern.split("\\\\moduleName\\\\".toRegex()).toTypedArray()

        if (namespaceSplit.size <= 1) {
            return moduleDirectory
        }

        val subDirectoryNames = namespaceSplit[namespaceSplit.size - 1].split("\\\\".toRegex()).toTypedArray()
        var currentDirectory: PsiDirectory = moduleDirectory

        for (subDirectoryName in subDirectoryNames) {
            val subDirectory: PsiDirectory? = currentDirectory.findSubdirectory(subDirectoryName)

            currentDirectory = subDirectory ?: this.createSubdirectory(currentDirectory, subDirectoryName)
        }

        return currentDirectory
    }

    override fun isAvailable(dataContext: DataContext): Boolean {
        val view: IdeView = LangDataKeys.IDE_VIEW.getData(dataContext) as IdeView
        val dir: PsiDirectory = view.orChooseDirectory ?: return false

        if (dir.name != this.applicationName) {
            return false
        }

        val projectDir: PsiDirectory = dir.parent ?: return false
        val srcDir: PsiDirectory = projectDir.parent ?: return false

        return srcDir.name == "src"
    }

    @Throws(Exception::class)
    protected fun createSubdirectory(fileDirectory: PsiDirectory, subDirectoryName: String): PsiDirectory {
        return this.getProject().service<FileWriterInterface>()
            .createSubdirectory(fileDirectory, subDirectoryName) ?: throw Exception()
    }

    private fun getProject(): Project {
        return this.project ?: throw Exception()
    }

    override fun getCommandName(): String = this.actionName

    override fun getActionName(psiDirectory: PsiDirectory, s: String): String = this.actionName
}
