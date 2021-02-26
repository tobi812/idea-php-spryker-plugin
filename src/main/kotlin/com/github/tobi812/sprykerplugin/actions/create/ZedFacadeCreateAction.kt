package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import pav.sprykerFileCreator.SprykerIcons
import pav.sprykerFileCreator.model.generator.SprykerConstants
import java.lang.Exception

class ZedFacadeCreateAction :
    AbstractCreateClassTypeAction("Create Zed Facade", "Create Zed Facade", SprykerIcons.SPRYKER_ICON) {
    @Throws(Exception::class)
    override fun create(s: String, psiDirectory: PsiDirectory): Array<PsiElement> {
        return arrayOfNulls(0)
    }

    override fun getErrorTitle(): String {
        return null
    }

    override fun getCommandName(): String {
        return "Create Zed Facade"
    }

    override fun getActionName(psiDirectory: PsiDirectory, s: String): String {
        return "Create Zed Facade"
    }

    override val classType: String
        protected get() = SprykerConstants.ZED_FACADE
}