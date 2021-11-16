package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.constants.SprykerConstants
import com.github.tobi812.sprykerplugin.SprykerIcons

class ClientDependencyProviderCreateAction : AbstractCreateClassTypeAction(
    "Create ClientDependencyProvider",
    "Create ClientDependencyProvider",
    SprykerIcons.SPRYKER_ICON
) {
    override val actionName: String =  "Create ClientDependencyProvider"
    override val classType: String = SprykerConstants.CLIENT_DEPENDENCY_PROVIDER
}
