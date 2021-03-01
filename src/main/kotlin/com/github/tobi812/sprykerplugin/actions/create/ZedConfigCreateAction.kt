package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.SprykerIcons
import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.constants.SprykerConstants

class ZedConfigCreateAction :
    AbstractCreateClassTypeAction("Create Zed Config", "Create Zed Config", SprykerIcons.SPRYKER_ICON) {
    override val actionName: String = "Create Zed Config"
    override val classType: String = SprykerConstants.ZED_CONFIG
}