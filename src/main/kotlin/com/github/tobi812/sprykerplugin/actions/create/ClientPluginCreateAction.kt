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

class ClientPluginCreateAction :
    AbstractCreateClassTypeAction("Create Client Plugin", "Create Client Plugin", SprykerIcons.SPRYKER_ICON) {
    override val actionName: String =  "Create Client Plugin"
    override val classType: String = SprykerConstants.CLIENT_PLUGIN

    override fun invokeDialog(project: Project, psiDirectory: PsiDirectory): Array<PsiElement?> {
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

        return this.createClassType(project, psiDirectory, controllerName)
    }
}