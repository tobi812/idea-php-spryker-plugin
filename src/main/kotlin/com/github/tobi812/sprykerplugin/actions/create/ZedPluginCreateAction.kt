package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.SprykerIcons
import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.constants.SprykerConstants

class ZedPluginCreateAction :
    AbstractCreateClassTypeAction("Create Zed Plugin", "Create Zed Plugin", SprykerIcons.SPRYKER_ICON) {
    override val actionName: String = "Create Zed Plugin"
    override val classType: String = SprykerConstants.ZED_PLUGIN

    init {
        this.inputMessage = "Set Plugin Name"
        this.inputTitle = "Set Plugin Name"
    }
}
