package com.github.tobi812.sprykerplugin.models.manager

import com.github.tobi812.sprykerplugin.actions.ClassConfig
import com.github.tobi812.sprykerplugin.models.generator.ClassGeneratorInterface
import com.github.tobi812.sprykerplugin.models.renderer.PhpClassRendererInterface
import com.github.tobi812.sprykerplugin.models.renderer.dto.PhpClassInterface
import com.github.tobi812.sprykerplugin.models.resolver.PathResolverInterface
import com.github.tobi812.sprykerplugin.models.writer.FileWriterInterface
import com.intellij.psi.PsiElement
import java.lang.Exception

class ClassManager(
    private val classGenerator: ClassGeneratorInterface,
    private val classRenderer: PhpClassRendererInterface,
    private val pathResolver: PathResolverInterface,
    private val fileWriter: FileWriterInterface
) : ClassManagerInterface {

    @Throws(Exception::class)
    override fun handleClass(classType: String, classConfig: ClassConfig): PsiElement {
        val phpClass: PhpClassInterface = this.classGenerator.generateClass(classType, classConfig)
        val filePath: String = this.pathResolver.resolvePath(phpClass.namespace)
        val phpClassContent: String = this.classRenderer.renderPhpClass(phpClass)

        return this.writeFile(filePath, phpClass.name, phpClassContent)
    }

    @Throws(Exception::class)
    private fun writeFile(filePath: String, fileName: String, phpClassContent: String): PsiElement {
        return this.fileWriter.writeFile(filePath, fileName, phpClassContent)
    }
}