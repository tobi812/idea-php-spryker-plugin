package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.SprykerIcons
import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.constants.SprykerConstants

class ZedCommunicationFactoryCreateAction : AbstractCreateClassTypeAction(
    "Create Zed CommunicationFactory",
    "Create Zed CommunicationFactory",
    SprykerIcons.SPRYKER_ICON
) {
    override val actionName: String =  "Create Zed Communication Factory"
    override val classType: String = SprykerConstants.ZED_COMMUNICATION_FACTORY
}
