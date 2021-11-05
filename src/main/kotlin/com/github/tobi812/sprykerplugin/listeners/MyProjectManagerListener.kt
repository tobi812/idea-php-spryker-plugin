package com.github.tobi812.sprykerplugin.listeners

import com.github.tobi812.sprykerplugin.config.SprykerPluginConfig
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        project.service<SprykerPluginConfig>()
    }
}
