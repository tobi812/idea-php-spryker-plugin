package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.SprykerIcons

class YvesControllerCreateAction :
    AbstractCreateClassTypeAction("Create Yves Controller", "Create Yves Controller", SprykerIcons.SPRYKER_ICON) {

    override val actionName: String = "Create Yves Controller"
    override val classType: String = SprykerConstants.YVES_CONTROLLER

    init {
        this.inputMessage = "Set Controller Name"
        this.inputTitle = "Set Controller Name"
    }
}