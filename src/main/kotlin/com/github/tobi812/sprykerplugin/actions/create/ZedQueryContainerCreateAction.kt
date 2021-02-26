package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import pav.sprykerFileCreator.SprykerIcons
import java.lang.Exception

class ZedQueryContainerCreateAction :
    AbstractCreateClassTypeAction("Create Zed QueryContainer", "Create Zed QueryContainer", SprykerIcons.SPRYKER_ICON) {
    @Throws(Exception::class)
    override fun create(s: String, psiDirectory: PsiDirectory): Array<PsiElement> {
        val psiElements: Array<PsiElement> =
        return arrayOfNulls(0)
    }

    override fun getErrorTitle(): String {
        return ""
    }

    override fun getCommandName(): String {
        return "Create Zed QueryContainer"
    }

    override fun getActionName(psiDirectory: PsiDirectory, s: String): String {
        return "Create Zed QueryContainer"
    }

    override val classType: String
        get() = SprykerConstants.ZED_QUERY_CONTAINER
}