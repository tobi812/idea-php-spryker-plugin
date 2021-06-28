package com.github.tobi812.sprykerplugin.actions

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.models.ModelFactory
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface
import com.github.tobi812.sprykerplugin.models.manager.ClassManagerInterface
import com.github.tobi812.sprykerplugin.models.matcher.ClassTypeMatcherInterface
import com.intellij.ide.actions.CreateElementActionBase
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import java.lang.Exception
import java.util.function.Consumer
import javax.swing.Icon

abstract class AbstractCreateClassTypeAction protected constructor(text: String, description: String, icon: Icon?) :
    CreateElementActionBase(text, description, icon) {
    private val modelFactory: ModelFactory = ModelFactory()
    private var project: Project? = null
    protected abstract val classType: String
    protected abstract val actionName: String
    protected var inputMessage: String = ""
    protected var inputTitle: String = ""
    protected var initalValue: String = ""

    override fun invokeDialog(
        project: Project,
        directory: PsiDirectory,
        elementsConsumer: Consumer<Array<PsiElement?>>
    ) {
        this.project = project

        if (this.inputMessage == "" && this.inputTitle == "") {
            this.create("", directory)
        }

        val validator = MyInputValidator(project, directory)
        val moduleName = Messages.showInputDialog(
            this.inputMessage,
            this.inputTitle,
            Messages.getQuestionIcon(),
            this.initalValue,
            validator
        )

        if (moduleName == "" || moduleName == null) {
            return
        }

        elementsConsumer.accept(validator.createdElements)
    }

    private fun getProject(): Project {
        return this.project ?: throw Exception()
    }

    @Throws(Exception::class)
    override fun create(newName: String, directory: PsiDirectory): Array<PsiElement> {
        return this.createClassType(newName, directory)
    }

    protected open fun createClassType(
        className: String,
        psiDirectory: PsiDirectory
    ): Array<PsiElement> {
        val classType: String = this.classType

        val classTypeMatcher: ClassTypeMatcherInterface = this.modelFactory.classTypeMatcher
        val bundleName: String = classTypeMatcher.matchBundleName(classType, psiDirectory)
        val projectName: String = classTypeMatcher.matchProjectName(classType, psiDirectory)
        val classConfig = ClassConfig(bundleName, projectName, className)
        val classManager: ClassManagerInterface = this.modelFactory.createClassManager(this.getProject(), projectName)

        val psiElement = classManager.handleClass(psiDirectory, classType, classConfig)

        return arrayOf(psiElement)
    }

    override fun isAvailable(dataContext: DataContext): Boolean {
        val view = LangDataKeys.IDE_VIEW.getData(dataContext) ?: return false
        val dir = view.orChooseDirectory ?: return false

        return this.classTypeMatchesDir(dir)
    }

    override fun getCommandName(): String = this.actionName

    override fun getActionName(psiDirectory: PsiDirectory, s: String): String = this.actionName

    override fun getErrorTitle(): String = ""

    protected open fun classTypeMatchesDir(directory: PsiDirectory): Boolean {
        return try {
            val classType: String = this.classType
            val classTypeMatcher = this.modelFactory.classTypeMatcher

            if (!classTypeMatcher.classTypeMatchesDir(classType, directory)) {
                return false
            }

            val bundleName = classTypeMatcher.matchBundleName(classType, directory)
            val classDefinition: ClassDefinitionInterface = this.modelFactory
                .definitionProvider
                .getDefinitionByType(classType)
            val className: String = classDefinition.namePattern
                .replace(SprykerConstants.MODULE_NAME_PLACEHOLDER, bundleName)

            directory.findFile("$className.php") == null
        } catch (exception: Exception) {
            false
        }
    }
}
