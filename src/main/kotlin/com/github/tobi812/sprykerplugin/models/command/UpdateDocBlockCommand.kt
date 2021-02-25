package com.github.tobi812.sprykerplugin.models.command

import com.intellij.openapi.project.Project
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.elements.PhpClass
import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface
import pav.sprykerFileCreator.model.generator.DocBlockGeneratorInterface
import pav.sprykerFileCreator.model.matcher.ClassTypeMatcherInterface
import pav.sprykerFileCreator.model.renderer.PhpClassRendererInterface
import pav.sprykerFileCreator.model.renderer.dto.DocBlockItem
import java.lang.Exception

class UpdateDocBlockCommand(
        classTypeMatcher: ClassTypeMatcherInterface,
        docBlockGenerator: DocBlockGeneratorInterface,
        phpRenderer: PhpClassRendererInterface
) {
    private val classTypeMatcher: ClassTypeMatcherInterface
    private val docBlockGenerator: DocBlockGeneratorInterface
    private val phpRenderer: PhpClassRendererInterface

    init {
        this.classTypeMatcher = classTypeMatcher
        this.docBlockGenerator = docBlockGenerator
        this.phpRenderer = phpRenderer
    }

    @Throws(Exception::class)
    fun updateDocBlock(phpClass: PhpClass, project: Project?) {
        val fqClassName = phpClass.fqn
        val classDefinition: ClassDefinitionInterface = classTypeMatcher.matchClassType(fqClassName)
        val bundleName: String = classTypeMatcher.matchBundleNameFromFQName(classDefinition, fqClassName)
        val oldComment = phpClass.docComment
        val docBlockItems: List<DocBlockItem> = docBlockGenerator.getDocBlockItems(classDefinition.getDocBlockClasses(), bundleName)
        val comment = PhpPsiElementFactory.createFromText(
                project!!,
                PhpDocComment::class.java,
                phpRenderer.renderDocBlock(docBlockItems)
        ) ?: return
        if (oldComment == null) {
            phpClass.addBefore(comment, phpClass.firstChild)
        } else {
            oldComment.replace(comment)
        }
    }
}