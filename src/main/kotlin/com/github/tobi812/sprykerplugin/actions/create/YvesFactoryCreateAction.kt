package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.SprykerIcons
import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.constants.SprykerConstants

class YvesFactoryCreateAction : AbstractCreateClassTypeAction(
    "Create Yves CommunicationFactory",
    "Create Yves CommunicationFactory",
    SprykerIcons.SPRYKER_ICON
) {
    override val actionName: String =  "Create Yves Factory"
    override val classType: String = SprykerConstants.YVES_FACTORY
}