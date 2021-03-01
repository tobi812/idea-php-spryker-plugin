package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.SprykerIcons
import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.constants.SprykerConstants

class ZedQueryContainerCreateAction :
    AbstractCreateClassTypeAction("Create Zed QueryContainer", "Create Zed QueryContainer", SprykerIcons.SPRYKER_ICON) {
    override val actionName: String = "Create Zed QueryContainer"
    override val classType: String = SprykerConstants.ZED_QUERY_CONTAINER
}