package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.SprykerIcons
import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.constants.SprykerConstants

class ZedDependencyProviderCreateAction : AbstractCreateClassTypeAction(
    "Create ZedDependencyProvider",
    "Create ZedDependencyProvider",
    SprykerIcons.SPRYKER_ICON
) {
    override val actionName: String  = "Create ZedDependencyProvider"
    override val classType: String  = SprykerConstants.ZED_DEPENDENCY_PROVIDER
}