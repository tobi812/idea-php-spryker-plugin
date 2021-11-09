package com.github.tobi812.sprykerplugin.actions

import com.github.tobi812.sprykerplugin.models.ModelFactory
import com.github.tobi812.sprykerplugin.models.definitions.spryker.*
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.util.IncorrectOperationException
import com.jetbrains.php.lang.psi.PhpPsiUtil
import com.jetbrains.php.lang.psi.elements.PhpClass

class AddMissingFactoryMethodsAction : PsiElementBaseIntentionAction() {
    @Throws(IncorrectOperationException::class)
    override fun invoke(project: Project, editor: Editor, psiElement: PsiElement) {
        val phpClass = PhpPsiUtil.getParentByCondition<PhpClass>(psiElement, PhpClass.INSTANCEOF) ?: return
        CommandProcessor.getInstance().executeCommand(project, {
            ApplicationManager.getApplication().runWriteAction {
                val modelFactory = ModelFactory()
                modelFactory.createAddMissingFactoryMethodsCommand()
                    .addMissingFactoryMethods(project, psiElement)
            }
        }, "Add Spryker missing Factory methods", null)
    }

    override fun isAvailable(project: Project, editor: Editor, psiElement: PsiElement): Boolean {
        val phpClass = PhpPsiUtil.getParentByCondition<PhpClass>(psiElement, PhpClass.INSTANCEOF) ?: return false

        return this.isFactoryClass(phpClass)
    }

    override fun getFamilyName(): String {
        return "Spryker"
    }

    override fun getText(): String {
        return "Add missing factory methods"
    }

    private fun isFactoryClass(phpClass: PhpClass): Boolean {
        var currentClass: PhpClass? = phpClass.superClass

        val abstractFactories: Array<String> = arrayOf(
            ClientFactoryDefinition().defaultParentClass,
            YvesFactoryDefinition().defaultParentClass,
            ZedBusinessFactoryDefinition().defaultParentClass,
            ZedPersistenceFactoryDefinition().defaultParentClass,
            ZedCommunicationFactoryDefinition().defaultParentClass
        )

        while (currentClass is PhpClass) {
            val currentFqn: String = currentClass.fqn.removePrefix("\\")
            if (abstractFactories.contains(currentFqn)) {
                return true
            }
            currentClass = currentClass.superClass
        }

        return false
    }
}
