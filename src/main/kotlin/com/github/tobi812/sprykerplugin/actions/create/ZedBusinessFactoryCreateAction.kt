package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.SprykerIcons
import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.constants.SprykerConstants

class ZedBusinessFactoryCreateAction : AbstractCreateClassTypeAction(
    "Create Zed BusinessFactory",
    "Create Zed BusinessFactory",
    SprykerIcons.SPRYKER_ICON
) {
    override val actionName: String = "Create Zed Business Factory"
    override val classType: String = SprykerConstants.ZED_BUSINESS_FACTORY
}
