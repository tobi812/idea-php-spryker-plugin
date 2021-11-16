package com.github.tobi812.sprykerplugin.services.command

import com.github.tobi812.sprykerplugin.services.matcher.ClassTypeMatcherInterface
import com.github.tobi812.sprykerplugin.services.renderer.PhpClassRendererInterface
import com.github.tobi812.sprykerplugin.services.renderer.dto.DocBlockItem
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment
import com.jetbrains.php.lang.psi.PhpFile
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.PhpPsiUtil
import com.jetbrains.php.lang.psi.elements.*

class AddMissingFactoryMethodsCommand(
) {
    fun addMissingFactoryMethods(project: Project, psiElement: PsiElement) {
        val directory = psiElement.containingFile.containingDirectory

        if (directory !is PsiDirectory) {
            return
        }

        val mainClass = PhpPsiUtil.getParentByCondition<PhpClass>(psiElement, PhpClass.INSTANCEOF) ?: return
        val phpClasses = this.findPhpClasses(directory)
        val classTypeMatcher = project.service<ClassTypeMatcherInterface>()

        for (phpClass in phpClasses) {
            if (classTypeMatcher.isSprykerClass(phpClass.fqn) || phpClass.isAbstract || phpClass.isTrait || phpClass.isInterface) {
                continue
            }

            var method = mainClass.findMethodByName(StringBuffer("create" + phpClass.name))
            if (method is Method) {
                continue
            }

            val factoryMethod: String = project.service<PhpClassRendererInterface>().renderFactoryMethod(phpClass)
            method = PhpPsiElementFactory.createMethod(project, factoryMethod)
            val comment = this.createDocComment(phpClass, project)

            val mainClassParent = mainClass.parent

            if (mainClass.namespaceName != phpClass.namespaceName) {
                var useStatement: PhpUseList? = null
                if (mainClassParent is PhpPsiElement) {
                    useStatement = this.getUseStatement(mainClassParent, project, phpClass)
                }

                if (useStatement != null) {
                    mainClassParent.addAfter(useStatement, mainClass.parent.firstChild)
                }
            }

            if (comment != null) {
                mainClass.addBefore(comment, mainClass.lastChild)
            }

            mainClass.addBefore(method, mainClass.lastChild)
        }
    }

    private fun createDocComment(
        phpClass: PhpClass,
        project: Project
    ): PhpDocComment? {
        val docBlockItems = ArrayList<DocBlockItem>()

        var returnType: String = phpClass.fqn
        if (phpClass.implementedInterfaces.isNotEmpty()) {
            val firstInterface: PhpClass? = phpClass.implementedInterfaces.iterator().next()
            if (firstInterface is PhpClass) {
                returnType = firstInterface.fqn
            }
        }

        docBlockItems.add(DocBlockItem("return", returnType))

        val comment = PhpPsiElementFactory.createFromText(
            project,
            PhpDocComment::class.java,
            project.service<PhpClassRendererInterface>().renderDocBlock(docBlockItems)
        )
        return comment
    }

    private fun findPhpClasses(directory: PsiDirectory): ArrayList<PhpClass> {
        val phpClasses: ArrayList<PhpClass> = ArrayList()

        for (subdirectory in directory.subdirectories) {
            phpClasses.addAll(this.findPhpClasses(subdirectory))
        }

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

    private fun getUseStatement(mainClassParent: PhpPsiElement, project: Project, phpClass: PhpClass): PhpUseList? {
        var alias: String? = null
        for (child in mainClassParent.children) {
            if (child !is PhpUseList) {
                continue
            }

            for (use in child.declarations) {
                if (use.fqn == phpClass.fqn) {
                    return null
                }

                if (use.name == phpClass.name) {
                    alias = phpClass.name + "Alias"
                }
            }
        }

        return PhpPsiElementFactory.createUseStatement(project, phpClass.fqn, alias)
    }
}