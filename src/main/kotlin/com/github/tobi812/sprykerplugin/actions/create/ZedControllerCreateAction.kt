package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.NonEmptyInputValidator
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import org.apache.commons.lang.StringUtils
import pav.sprykerFileCreator.SprykerIcons
import pav.sprykerFileCreator.model.generator.SprykerConstants
import java.lang.Exception
import java.util.*

class ZedControllerCreateAction :
    AbstractCreateClassTypeAction("Create Zed Controller", "Create Zed Controller", SprykerIcons.SPRYKER_ICON) {
    override fun invokeDialog(project: Project, psiDirectory: PsiDirectory): Array<PsiElement> {
        val classConfig = HashMap<String, String?>()
        val controllerName = Messages.showInputDialog(
            "Set Controller Name",
            "Input Controller Name",
            Messages.getQuestionIcon(),
            "",
            NonEmptyInputValidator()
        )
        if (StringUtils.isBlank(controllerName)) {
            return arrayOfNulls(1)
        }
        classConfig[SprykerConstants.CLASS_NAME] = controllerName
        return createClassType(project, psiDirectory, classConfig)
    }

    @Throws(Exception::class)
    override fun create(s: String, psiDirectory: PsiDirectory): Array<PsiElement> {
        return arrayOfNulls(0)
    }

    override fun getErrorTitle(): String {
        return null
    }

    override fun getCommandName(): String {
        return "Create Zed Controller"
    }

    override fun getActionName(psiDirectory: PsiDirectory, s: String): String {
        return "Create Zed Controller"
    }

    override val classType: String
        protected get() = SprykerConstants.ZED_CONTROLLER
}