package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import pav.sprykerFileCreator.SprykerIcons
import pav.sprykerFileCreator.model.generator.SprykerConstants
import java.lang.Exception

class ClientCreateAction :
    AbstractCreateClassTypeAction("Create Client", "Create Client", SprykerIcons.SPRYKER_ICON) {

    override fun getErrorTitle(): String {
        return null
    }

    override fun getCommandName(): String {
        return "Create Client"
    }

    override fun getActionName(psiDirectory: PsiDirectory, s: String): String {
        return "Create Client"
    }

    override fun getClassType(): String {
        return SprykerConstants.CLIENT
    }
}