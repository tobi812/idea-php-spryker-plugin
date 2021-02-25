package com.github.tobi812.ideaphpsprykerplugin.services

import com.github.tobi812.ideaphpsprykerplugin.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
