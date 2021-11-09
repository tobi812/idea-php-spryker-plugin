package com.github.tobi812.sprykerplugin.models.command

import com.github.tobi812.sprykerplugin.models.matcher.ClassTypeMatcherInterface
import com.github.tobi812.sprykerplugin.models.renderer.PhpClassRendererInterface
import com.github.tobi812.sprykerplugin.models.renderer.dto.DocBlockItem
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment
import com.jetbrains.php.lang.psi.PhpFile
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.PhpPsiUtil
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.elements.PhpClass

class AddMissingFactoryMethodsCommand(
    private val phpClassRenderer: PhpClassRendererInterface,
    private val classTypeMatcher: ClassTypeMatcherInterface
) {
    fun addMissingFactoryMethods(project: Project, psiElement: PsiElement) {
        val directory = psiElement.containingFile.containingDirectory

        if (directory !is PsiDirectory) {
            return
        }

        val mainClass = PhpPsiUtil.getParentByCondition<PhpClass>(psiElement, PhpClass.INSTANCEOF) ?: return
        val phpClasses = this.findPhpClasses(directory)
        for (phpClass in phpClasses) {
            if (this.classTypeMatcher.isSprykerClass(phpClass.fqn) || phpClass.isAbstract || phpClass.isTrait || phpClass.isAbstract) {
                continue
            }

            var method = mainClass.findMethodByName(StringBuffer("create" + phpClass.name))
            if (method is Method) {
                continue
            }

            val factoryMethod: String = phpClassRenderer.renderFactoryMethod(phpClass)
            method = PhpPsiElementFactory.createMethod(project, factoryMethod)
            val docBlockItems = ArrayList<DocBlockItem>()

            var returnType: String = phpClass.fqn
            if (phpClass.implementedInterfaces.size > 0) {
                val firstInterface: PhpClass? = phpClass.implementedInterfaces.iterator().next()
                if (firstInterface is PhpClass) {
                    returnType = firstInterface.fqn
                }
            }

            docBlockItems.add(DocBlockItem("return", returnType))

            val comment = PhpPsiElementFactory.createFromText(
                project,
                PhpDocComment::class.java,
                this.phpClassRenderer.renderDocBlock(docBlockItems)
            )

            if (comment != null) {
                mainClass.addBefore(comment, mainClass.lastChild)
            }
            mainClass.addBefore(method, mainClass.lastChild)
        }
    }

    private fun findPhpClasses(directory: PsiDirectory): ArrayList<PhpClass> {
        val phpClasses: ArrayList<PhpClass> = ArrayList()

        for (file in directory.files) {
            if (file !is PhpFile) {
                continue
            }

            val moduleClass = PhpPsiUtil.findAllClasses(file).iterator().next()

            if (moduleClass is PhpClass) {
                phpClasses.add(moduleClass)
            }
        }

        return phpClasses
    }
}