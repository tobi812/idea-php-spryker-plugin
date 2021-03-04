package com.github.tobi812.sprykerplugin.actions.create.bundle

import com.github.tobi812.sprykerplugin.SprykerIcons
import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.intellij.psi.PsiDirectory
import java.util.ArrayList

class ClientCreateModuleAction private constructor() :
    AbstractCreateModuleAction(
        "Create Client Module",
        "Create Client Module",
        SprykerIcons.SPRYKER_ICON
    ) {

    override fun getErrorTitle(): String {
        return "Creating Client Module Failed!"
    }

    override fun getActionName(psiDirectory: PsiDirectory, s: String): String {
        return "Create Client Bundle"
    }

    override val applicationName: String
        get() = "Client"

    override val classTypes: ArrayList<String>
        get() {
            val classTypes = ArrayList<String>()
            classTypes.add(SprykerConstants.CLIENT_DEPENDENCY_PROVIDER)
            classTypes.add(SprykerConstants.CLIENT_FACTORY)
            classTypes.add(SprykerConstants.CLIENT)

            return classTypes
        }
}