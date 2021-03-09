package com.github.tobi812.sprykerplugin.actions.create.bundle

import com.github.tobi812.sprykerplugin.SprykerIcons
import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.intellij.psi.PsiDirectory
import java.util.ArrayList

class ZedCreateModuleAction private constructor() :
    AbstractCreateModuleAction(
        "Create Zed Bundle",
        "Create Zed Bundle",
        SprykerIcons.SPRYKER_ICON
    ) {

    override fun getErrorTitle(): String {
        return "Creating Zed Module Failed!"
    }

    override fun getActionName(psiDirectory: PsiDirectory, s: String): String {
        return "Create Zed Bundle"
    }

    override val applicationName: String
         get() = "Zed"

    override val classTypes: ArrayList<String>
        get() {
            val classTypes = ArrayList<String>()
            classTypes.add(SprykerConstants.ZED_DEPENDENCY_PROVIDER)
            classTypes.add(SprykerConstants.ZED_CONFIG)
            classTypes.add(SprykerConstants.ZED_PERSISTENCE_FACTORY)
            classTypes.add(SprykerConstants.ZED_QUERY_CONTAINER)
            classTypes.add(SprykerConstants.ZED_BUSINESS_FACTORY)
            classTypes.add(SprykerConstants.ZED_FACADE)
            classTypes.add(SprykerConstants.ZED_COMMUNICATION_FACTORY)

            return classTypes
        }
}
