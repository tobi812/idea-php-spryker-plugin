package com.github.tobi812.sprykerplugin.actions.create

import com.github.tobi812.sprykerplugin.SprykerIcons
import com.github.tobi812.sprykerplugin.actions.AbstractCreateClassTypeAction
import com.github.tobi812.sprykerplugin.constants.SprykerConstants

class ZedPersistenceFactoryCreateAction : AbstractCreateClassTypeAction(
    "Create Zed PersistenceFactory",
    "Create Zed PersistenceFactory",
    SprykerIcons.SPRYKER_ICON
) {
    override val actionName: String = "Create Zed Persistence Factory"
    override val classType: String = SprykerConstants.ZED_PERSISTENCE_FACTORY
}
