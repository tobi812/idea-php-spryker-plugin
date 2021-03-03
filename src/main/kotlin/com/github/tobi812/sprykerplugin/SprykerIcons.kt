package com.github.tobi812.sprykerplugin

import com.intellij.openapi.util.IconLoader

interface SprykerIcons {
    companion object {
        val SPRYKER_ICON = IconLoader.getIcon("icons/sprykerLogo.png", SprykerIcons::class.java)
    }
}