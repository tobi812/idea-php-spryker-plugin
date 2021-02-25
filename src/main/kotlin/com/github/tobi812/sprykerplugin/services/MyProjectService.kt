package com.github.tobi812.sprykerplugin.services

import com.github.tobi812.sprykerplugin.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
