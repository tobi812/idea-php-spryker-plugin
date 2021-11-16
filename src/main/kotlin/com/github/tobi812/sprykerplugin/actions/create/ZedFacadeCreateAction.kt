package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.SprykerIcons
import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.constants.SprykerConstants

class ZedFacadeCreateAction :
    AbstractCreateClassTypeAction("Create Zed Facade", "Create Zed Facade", SprykerIcons.SPRYKER_ICON) {
    override val actionName = "Create Zed Facade"
    override val classType: String = SprykerConstants.ZED_FACADE
}
