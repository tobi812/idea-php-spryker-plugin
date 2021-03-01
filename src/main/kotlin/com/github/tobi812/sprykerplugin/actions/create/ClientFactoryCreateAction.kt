package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.SprykerIcons

class ClientFactoryCreateAction :
    AbstractCreateClassTypeAction("Create ClientFactory", "Create ClientFactory", SprykerIcons.SPRYKER_ICON) {
    override val actionName: String = "Create ClientFactory"
    override val classType: String = SprykerConstants.CLIENT_FACTORY
}