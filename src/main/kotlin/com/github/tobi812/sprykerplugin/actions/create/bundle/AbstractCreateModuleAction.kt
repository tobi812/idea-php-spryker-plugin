package com.github.tobi812.sprykerplugin.actions.create.bundle

import com.github.tobi812.sprykerplugin.actions.ClassConfig
import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.models.ModelFactory
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface
import com.github.tobi812.sprykerplugin.models.definitions.DefinitionProviderInterface
import com.github.tobi812.sprykerplugin.models.manager.ClassManagerInterface
import com.intellij.ide.IdeView
import com.intellij.ide.actions.CreateElementActionBase
import com.intellij.ide.actions.ElementCreator
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.InputValidator
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.NonEmptyInputValidator
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import java.lang.Exception
import java.util.*
import java.util.function.Consumer
import javax.swing.Icon

// Refactor in this manner:
// https://github.com/JetBrains/intellij-community/blob/19306e6d29182ef0d906e7f60054e0de11861da0/plugins/devkit/devkit-core/src/actions/NewMessageBundleAction.kt
@Suppress("NAME_SHADOWING")
abstract class AbstractCreateModuleAction protected constructor(text: String?, description: String?, icon: Icon?) :
    CreateElementActionBase(text, description, icon) {
    protected var modelFactory: ModelFactory = ModelFactory()
    private var project: Project? = null
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

        if (directory.findSubdirectory(moduleName) != null) {
            Messages.showErrorDialog(project, "Module $moduleName already exists!", "Error")

            return
        }
        if (moduleName != null) {
            elementsConsumer.accept(validator.createdElements)
        }

        try {
            val moduleDirectory: PsiDirectory = this.createSubdirectory(directory, moduleName)
            val projectName = this.matchProjectName(directory)
            val classConfig = ClassConfig(moduleName, projectName)

            this.createModuleClasses(project, moduleDirectory, classConfig)
        } catch (exception: Exception) {
            exception.printStackTrace()
            Messages.showErrorDialog(project, "Error:" + exception.message, "Error")
        }
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
    ) {
        val projectName = classConfig.projectName
        val classManager: ClassManagerInterface = this.modelFactory.createClassManager(project, projectName)
        val definitionProvider: DefinitionProviderInterface = this.modelFactory.createDefinitionProvider()

        for (classType in this.classTypes) {
            val classDefinition: ClassDefinitionInterface = definitionProvider.getDefinitionByType(classType)
            val classDirectory: PsiDirectory = this.resolveClassDirectory(classDefinition, moduleDirectory)

            classManager.handleClass(classDirectory, classType, classConfig)
        }
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

        if (dir.name != applicationName) {
            return false
        }

        val projectDir: PsiDirectory = dir.parent ?: return false
        val srcDir: PsiDirectory = projectDir.parent ?: return false

        return srcDir.name == "src"
    }

    @Throws(Exception::class)
    protected fun createSubdirectory(fileDirectory: PsiDirectory, subDirectoryName: String): PsiDirectory {
        val subDirectory: PsiDirectory = this.modelFactory
            .createFileWriter(this.getProject())
            .createSubdirectory(fileDirectory, subDirectoryName) ?: throw Exception()

        return subDirectory
    }

    protected fun getProject(): Project {
        val project: Project = this.project ?: throw Exception()

        return project
    }

    protected open inner class MyInputValidator(project: Project?, val directory: PsiDirectory) :
        ElementCreator(project, this.getErrorTitle()), InputValidator {
        var createdElements = PsiElement.EMPTY_ARRAY
            private set

        override fun checkInput(inputString: String): Boolean {
            return !inputString.isEmpty()
        }

        @Throws(Exception::class)
        public override fun create(newName: String): Array<PsiElement> {
            return this@AbstractCreateModuleAction.create(newName, directory)
        }

        override fun startInWriteAction(): Boolean {
            return this@AbstractCreateModuleAction.startInWriteAction()
        }

        public override fun getActionName(newName: String): String {
            return this@AbstractCreateModuleAction.getActionName(directory, newName)!!
        }

        override fun canClose(inputString: String): Boolean {
            this.createdElements = this.tryCreate(inputString)

            return this.checkInput(inputString) && createdElements.size > 0
        }
    }
}