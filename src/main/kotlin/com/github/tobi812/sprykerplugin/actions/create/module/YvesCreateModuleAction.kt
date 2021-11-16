package com.github.tobi812.sprykerplugin.actions.create.module

import com.github.tobi812.sprykerplugin.SprykerIcons
import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import java.util.ArrayList

class YvesCreateModuleAction private constructor() :
    AbstractCreateModuleAction(
        "Create Yves Bundle",
        "Create Yves Bundle",
        SprykerIcons.SPRYKER_ICON
    ) {

    override val actionName: String = "Create Yves Bundle"

    override fun getErrorTitle(): String {
        return "Creating Yves Module Failed!"
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
