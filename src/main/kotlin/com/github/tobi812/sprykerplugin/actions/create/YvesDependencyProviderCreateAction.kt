package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.SprykerIcons
import com.github.tobi812.sprykerplugin.constants.SprykerConstants

class YvesDependencyProviderCreateAction : AbstractCreateClassTypeAction(
    "Create YvesDependencyProvider",
    "Create YvesDependencyProvider",
    SprykerIcons.SPRYKER_ICON
) {
    override val actionName: String = "Create YvesDependencyProvider"
    override val classType: String = SprykerConstants.YVES_DEPENDENCY_PROVIDER
}