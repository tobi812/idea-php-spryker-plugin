package com.github.tobi812.sprykerplugin.models.command

import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface
import com.github.tobi812.sprykerplugin.models.generator.DocBlockGeneratorInterface
import com.github.tobi812.sprykerplugin.models.matcher.ClassTypeMatcherInterface
import com.github.tobi812.sprykerplugin.models.renderer.PhpClassRendererInterface
import com.github.tobi812.sprykerplugin.models.renderer.dto.DocBlockItem
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.elements.PhpClass
import java.lang.Exception

class UpdateDocBlockCommand() {
    @Throws(Exception::class)
    fun updateDocBlock(phpClass: PhpClass, project: Project) {
        val fqClassName = phpClass.fqn
        val classTypeMatcher = project.service<ClassTypeMatcherInterface>()
        val classDefinition: ClassDefinitionInterface = classTypeMatcher.matchClassType(fqClassName)
            ?: throw Exception("Class definition was not found: $fqClassName")

        val bundleName: String = classTypeMatcher.matchBundleNameFromFQName(classDefinition, fqClassName)
        val oldComment = phpClass.docComment
        val docBlockGenerator = project.service<DocBlockGeneratorInterface>()
        val docBlockItems: List<DocBlockItem> = docBlockGenerator.getDocBlockItems(classDefinition.docBlockClasses, bundleName)
        val comment = PhpPsiElementFactory.createFromText(
                project,
                PhpDocComment::class.java,
                project.service<PhpClassRendererInterface>().renderDocBlock(docBlockItems)
        ) ?: return

        if (oldComment == null) {
            phpClass.addBefore(comment, phpClass.firstChild)
        } else {
            oldComment.replace(comment)
        }
    }
}
