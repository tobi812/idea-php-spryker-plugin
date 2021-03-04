package com.github.tobi812.sprykerplugin.actions.create.bundle

import com.github.tobi812.sprykerplugin.SprykerIcons
import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import java.lang.Exception
import java.util.ArrayList

class YvesCreateModuleAction protected constructor() :
    AbstractCreateModuleAction(
        "Create Yves Bundle",
        "Create Yves Bundle",
        SprykerIcons.SPRYKER_ICON
    ) {

    @Throws(Exception::class)
    override fun create(newName: String, psiDirectory: PsiDirectory): Array<PsiElement> {
        return arrayOfNulls<PsiElement>(0)
    }

    override fun getErrorTitle(): String {
        return ""
    }
    override fun getActionName(psiDirectory: PsiDirectory, s: String): String {
        return "Create Yves Bundle"
    }

    override val applicationName: String
        get() = "Yves"

    override val classTypes: ArrayList<String>
        get() {
            val classTypes = ArrayList<String>()
            classTypes.add(SprykerConstants.YVES_FACTORY)
            classTypes.add(SprykerConstants.YVES_DEPENDENCY_PROVIDER)

            return classTypes
        }
}