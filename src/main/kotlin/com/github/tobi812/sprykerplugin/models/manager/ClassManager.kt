package com.github.tobi812.sprykerplugin.models.manager

import com.github.tobi812.sprykerplugin.actions.ClassConfig
import com.github.tobi812.sprykerplugin.models.generator.ClassGeneratorInterface
import com.github.tobi812.sprykerplugin.models.renderer.PhpClassRendererInterface
import com.github.tobi812.sprykerplugin.models.writer.FileWriterInterface
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import java.lang.Exception

class ClassManager(
    private val classGenerator: ClassGeneratorInterface,
    private val classRenderer: PhpClassRendererInterface,
    private val fileWriter: FileWriterInterface
) : ClassManagerInterface {

    @Throws(Exception::class)
    override fun handleClass(
        fileDirectory: PsiDirectory,
        classType: String,
        classConfig: ClassConfig
    ): PsiElement {
        val phpClass = this.classGenerator.generateClass(classType, classConfig)
        val phpClassContent = this.classRenderer.renderPhpClass(phpClass)

        return this.writeFile(fileDirectory, phpClass.name, phpClassContent)
    }

    @Throws(Exception::class)
    private fun writeFile(fileDirectory: PsiDirectory, fileName: String, phpClassContent: String): PsiElement {
        return this.fileWriter.writeFile(fileDirectory, fileName, phpClassContent)
    }
}