package com.github.tobi812.sprykerplugin.services.manager

import com.github.tobi812.sprykerplugin.actions.ClassConfig
import com.github.tobi812.sprykerplugin.services.generator.ClassGeneratorInterface
import com.github.tobi812.sprykerplugin.services.renderer.PhpClassRendererInterface
import com.github.tobi812.sprykerplugin.services.writer.FileWriterInterface
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import java.lang.Exception

class ClassManager(
    private val project: Project
) : ClassManagerInterface {

    @Throws(Exception::class)
    override fun handleClass(
        fileDirectory: PsiDirectory,
        classType: String,
        classConfig: ClassConfig
    ): PsiElement {
        val classGenerator = project.service<ClassGeneratorInterface>()
        val phpClass = classGenerator.generateClass(classType, classConfig)
        val phpClassContent = project.service<PhpClassRendererInterface>().renderPhpClass(phpClass)

        return project.service<FileWriterInterface>().writeFile(fileDirectory, phpClass.name, phpClassContent)
    }
}
