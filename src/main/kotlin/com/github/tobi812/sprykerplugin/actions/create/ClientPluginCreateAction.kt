package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.actions.ClassConfig
import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.intellij.ide.actions.CreateFileAction.MkDirs
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.NonEmptyInputValidator
import com.intellij.openapi.util.ThrowableComputable
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import org.apache.commons.lang.StringUtils
import pav.sprykerFileCreator.SprykerIcons
import pav.sprykerFileCreator.model.generator.SprykerConstants
import java.lang.Exception
import java.util.*

class ClientPluginCreateAction :
    AbstractCreateClassTypeAction("Create Client Plugin", "Create Client Plugin", SprykerIcons.SPRYKER_ICON) {
    override fun invokeDialog(project: Project, psiDirectory: PsiDirectory): Array<PsiElement> {
        val controllerName = Messages.showInputDialog(
            "Set Plugin Name",
            "Input Plugin Name",
            Messages.getQuestionIcon(),
            "",
            NonEmptyInputValidator()
        )
        if (StringUtils.isBlank(controllerName)) {
            return arrayOfNulls(1)
        }
        val classConfig: ClassConfig = ClassConfig(controllerName)

        return createClassType(project, psiDirectory, classConfig)
    }

    @Throws(Exception::class)
    override fun create(newName: String, directory: PsiDirectory): Array<PsiElement> {
        val mkdirs = MkDirs(newName, directory)
        return arrayOf(WriteAction.compute<PsiFile, RuntimeException> {
            mkdirs.directory.createFile(
                getFileName(mkdirs.newName)
            )
        })
    }

    override fun getErrorTitle(): String {
        return null
    }

    override fun getCommandName(): String {
        return "Create Client Plugin"
    }

    override fun getActionName(psiDirectory: PsiDirectory, s: String): String {
        return "Create Client Plugin"
    }

    override val classType: String
        get() = SprykerConstants.CLIENT_PLUGIN
}