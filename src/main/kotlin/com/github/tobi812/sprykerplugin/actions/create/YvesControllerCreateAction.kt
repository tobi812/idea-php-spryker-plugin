package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.NonEmptyInputValidator
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import org.apache.commons.lang.StringUtils
import com.github.tobi812.sprykerplugin.SprykerIcons

class YvesControllerCreateAction :
    AbstractCreateClassTypeAction("Create Yves Controller", "Create Yves Controller", SprykerIcons.SPRYKER_ICON) {

    override val actionName: String = "Create Yves Controller"
    override val classType: String = SprykerConstants.YVES_CONTROLLER

    override fun invokeDialog(project: Project, psiDirectory: PsiDirectory): Array<PsiElement?> {
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

        return this.createClassType(project, psiDirectory, controllerName)
    }
}