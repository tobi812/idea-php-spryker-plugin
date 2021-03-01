package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.SprykerIcons

class ClientCreateAction :
    AbstractCreateClassTypeAction("Create Client", "Create Client", SprykerIcons.SPRYKER_ICON) {
    override val actionName: String = "Create Client"
    override val classType: String = SprykerConstants.CLIENT
}