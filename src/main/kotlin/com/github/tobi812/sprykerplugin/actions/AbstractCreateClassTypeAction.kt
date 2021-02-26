package com.github.tobi812.sprykerplugin.actions

import com.github.tobi812.sprykerplugin.models.ModelFactory
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
import javax.swing.Icon

abstract class AbstractCreateClassTypeAction protected constructor(text: String, description: String, icon: Icon?) :
    CreateElementActionBase(text, description, icon) {

    private var modelFactory: ModelFactory = ModelFactory()

    protected abstract val classType: String

    override fun invokeDialog(project: Project, psiDirectory: PsiDirectory): Array<PsiElement?> {
        return this.createClassType(project, psiDirectory)
    }

    protected fun createClassType(
        project: Project,
        psiDirectory: PsiDirectory,
        className: String? = null
    ): Array<PsiElement?> {
        val classType = this.classType
        val psiElements = arrayOfNulls<PsiElement>(1)

        try {
            val classTypeMatcher: ClassTypeMatcherInterface = this.modelFactory.createClassTypeMatcher()
            val bundleName: String = classTypeMatcher.matchBundleName(classType, psiDirectory)
            val projectName: String = classTypeMatcher.matchProjectName(classType, psiDirectory)
            val classConfig = ClassConfig(bundleName, projectName, className)
            val classManager: ClassManagerInterface = this.modelFactory.createClassManager(project, projectName)
            val psiElement: PsiElement = classManager.handleClass(classType, classConfig)

            psiElements[0] = psiElement
        } catch (exception: Exception) {
            exception.printStackTrace()
            Messages.showErrorDialog(project, "Error:" + exception.message, "Error")
        }

        return psiElements
    }

    override fun isAvailable(dataContext: DataContext): Boolean {
        val view = LangDataKeys.IDE_VIEW.getData(dataContext) ?: return false
        val dir = view.orChooseDirectory ?: return false

        return classTypeMatchesDir(dir)
    }

    private fun classTypeMatchesDir(directory: PsiDirectory): Boolean {
        return try {
            val classTypeMatcher: ClassTypeMatcherInterface = this.modelFactory.createClassTypeMatcher()
            classTypeMatcher.classTypeMatchesDir(classType, directory)
        } catch (exception: Exception) {
            false
        }
    }
}