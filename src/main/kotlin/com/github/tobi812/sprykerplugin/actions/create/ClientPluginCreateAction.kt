package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.SprykerIcons

class ClientPluginCreateAction :
    AbstractCreateClassTypeAction("Create Client Plugin", "Create Client Plugin", SprykerIcons.SPRYKER_ICON) {
    override val actionName: String =  "Create Client Plugin"
    override val classType: String = SprykerConstants.CLIENT_PLUGIN

    init {
        this.inputMessage = "Set Plugin Name"
        this.inputTitle = "Set Plugin Name"
    }
}
