package com.github.tobi812.sprykerplugin.actions.create.bundle

import com.github.tobi812.sprykerplugin.SprykerIcons
import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.intellij.psi.PsiDirectory
import java.util.ArrayList

class YvesCreateModuleAction private constructor() :
    AbstractCreateModuleAction(
        "Create Yves Bundle",
        "Create Yves Bundle",
        SprykerIcons.SPRYKER_ICON
    ) {

    override fun getErrorTitle(): String {
        return "Creating Yves Module Failed!"
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
