package com.github.tobi812.sprykerplugin.actions

import com.github.tobi812.sprykerplugin.models.ModelFactory
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.util.IncorrectOperationException
import com.jetbrains.php.lang.psi.PhpPsiUtil
import com.jetbrains.php.lang.psi.elements.PhpClass
import java.lang.Exception

class SprykerUpdateClassDocBlockAction : PsiElementBaseIntentionAction() {
    @Throws(IncorrectOperationException::class)
    override fun invoke(project: Project, editor: Editor, psiElement: PsiElement) {
        val phpClass = PhpPsiUtil.getParentByCondition<PhpClass>(psiElement, PhpClass.INSTANCEOF) ?: return
        CommandProcessor.getInstance().executeCommand(project, {
            ApplicationManager.getApplication().runWriteAction {
                val modelFactory = ModelFactory()
                val classTypeMatcher: ClassTypeMatcherInterface = modelFactory.createClassTypeMatcher()
                val projectName: String = classTypeMatcher.matchProjectName(phpClass.fqn)
                val command: UpdateDocBlockCommand = modelFactory.createUpdateDocBlockCommand(project, projectName)
                try {
                    command.updateDocBlock(phpClass, project)
                } catch (exception: Exception) {
                    print(exception.toString())
                }
            }
        }, "Add Spryker DocBlock", null)
    }

    override fun isAvailable(project: Project, editor: Editor, psiElement: PsiElement): Boolean {
        val phpClass = PhpPsiUtil.getParentByCondition<PhpClass>(psiElement, PhpClass.INSTANCEOF) ?: return false
        val modelFactory = ModelFactory()
        val classTypeMatcher: ClassTypeMatcherInterface = modelFactory.createClassTypeMatcher()
        val classDefinition: ClassDefinitionInterface = classTypeMatcher.matchClassType(phpClass.fqn)
        return classDefinition != null
    }

    override fun getFamilyName(): String {
        return "Spryker"
    }

    override fun getText(): String {
        return "Update Spryker DocBlock"
    }
}