package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import java.lang.Exception

class ZedDependencyProviderCreateAction : AbstractCreateClassTypeAction(
    "Create ZedDependencyProvider",
    "Create ZedDependencyProvider",
    SprykerIcons.SPRYKER_ICON
) {
    @Throws(Exception::class)
    override fun create(s: String, psiDirectory: PsiDirectory): Array<PsiElement> {
        return arrayOfNulls(0)
    }

    override fun getErrorTitle(): String {
        return null
    }

    override fun getCommandName(): String {
        return "Create ZedDependencyProvider"
    }

    override fun getActionName(psiDirectory: PsiDirectory, s: String): String {
        return "Create ZedDependencyProvider"
    }

    override val classType: String
        protected get() = SprykerConstants.ZED_DEPENDENCY_PROVIDER
}