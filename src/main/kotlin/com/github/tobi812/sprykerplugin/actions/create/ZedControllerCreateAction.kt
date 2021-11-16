package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.SprykerIcons
import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.constants.SprykerConstants

class ZedControllerCreateAction :
    AbstractCreateClassTypeAction("Create Zed Controller", "Create Zed Controller", SprykerIcons.SPRYKER_ICON) {
    override val actionName: String = "Create Zed Controller"
    override val classType: String = SprykerConstants.ZED_CONTROLLER

    init {
        this.inputMessage = "Set Controller Name"
        this.inputTitle = "Set Controller Name"
    }
}
