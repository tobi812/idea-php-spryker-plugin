package com.github.tobi812.sprykerplugin.actions.create.bundle

import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.models.ModelFactory
import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface
import com.intellij.ide.IdeView
import com.intellij.ide.actions.CreateElementActionBase
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.application.Result
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiDirectory
import org.apache.commons.lang.StringUtils
import pav.sprykerFileCreator.model.ModelFactory
import java.lang.Exception
import java.util.*
import javax.swing.Icon

abstract class AbstractCreateBundleAction protected constructor(text: String?, description: String?, icon: Icon?) :
    CreateElementActionBase(text, description, icon) {
    protected var modelFactory: ModelFactory = ModelFactory()
    private var project: Project? = null
    abstract val applicationName: String
    abstract val classTypes: ArrayList<String?>

    override fun invokeDialog(project: Project, psiDirectory: PsiDirectory): Array<PsiElement> {
        val classConfig = HashMap<String, String?>()
        var psiElements: Array<PsiElement?> = arrayOfNulls<PsiElement>(1)
        val bundleName = Messages.showInputDialog(
            "Set Bundle Name",
            "Input Bundle Name",
            Messages.getQuestionIcon(),
            "",
            NonEmptyInputValidator()
        )
        if (StringUtils.isBlank(bundleName)) {
            return psiElements
        }
        this.project = project
        if (psiDirectory.findSubdirectory(bundleName) != null) {
            Messages.showErrorDialog(project, "Bundle $bundleName already exists!", "Error")
            return psiElements
        }
        try {
            val bundleDirectory: PsiDirectory = createSubdirectory(psiDirectory, bundleName)
            val projectName = matchProjectName(psiDirectory)
            classConfig[SprykerConstants.BUNDLE_NAME] = bundleName
            classConfig[SprykerConstants.PROJECT_NAME] = projectName
            psiElements = createBundleClasses(project, bundleDirectory, classConfig, psiElements)
        } catch (exception: Exception) {
            exception.printStackTrace()
            Messages.showErrorDialog(project, "Error:" + exception.message, "Error")
        }
        return psiElements
    }

    @Throws(Exception::class)
    private fun matchProjectName(psiDirectory: PsiDirectory): String {
        val projectDir: PsiDirectory = psiDirectory.getParent() ?: throw Exception()
        return projectDir.getName()
    }

    @Throws(Exception::class)
    protected fun createBundleClasses(
        project: Project?,
        bundleDirectory: PsiDirectory?,
        classConfig: HashMap<String, String?>,
        psiElements: Array<PsiElement?>
    ): Array<PsiElement?> {
        val projectName = classConfig[SprykerConstants.PROJECT_NAME]
        val classManager: ClassManagerInterface = modelFactory.createClassManager(project, projectName)
        val definitionProvider: DefinitionProviderInterface = modelFactory.createDefinitionProvider()
        for (classType in classTypes) {
            val classDefinition: ClassDefinitionInterface = definitionProvider.getDefinitionByType(classType)
            val classDirectory: PsiDirectory? = resolveClassDirectory(classDefinition, bundleDirectory)
            classManager.handleClass(classDirectory, classType, classConfig)
        }
        return psiElements
    }

    @Throws(Exception::class)
    protected fun resolveClassDirectory(
        classDirectory: ClassDefinitionInterface,
        bundleDirectory: PsiDirectory
    ): PsiDirectory? {
        var namespacePattern: String = classDirectory.namespacePattern
        namespacePattern = namespacePattern.replace(SprykerConstants.BUNDLE_NAME_PLACEHOLDER, "bundleName")
        val namespaceSplit = namespacePattern.split("\\\\bundleName\\\\".toRegex()).toTypedArray()
        if (namespaceSplit.size <= 1) {
            return bundleDirectory
        }
        val subDirectoryNames = namespaceSplit[namespaceSplit.size - 1].split("\\\\".toRegex()).toTypedArray()
        var currentDirectory: PsiDirectory = bundleDirectory
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
}