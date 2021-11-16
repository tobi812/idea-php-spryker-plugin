package com.github.tobi812.sprykerplugin.services.generator

import com.github.tobi812.sprykerplugin.services.definitions.ClassDefinitionInterface
import com.github.tobi812.sprykerplugin.services.definitions.DefinitionProviderInterface
import com.github.tobi812.sprykerplugin.services.renderer.dto.DocBlockItem
import com.github.tobi812.sprykerplugin.services.renderer.dto.PhpClassInterface
import com.github.tobi812.sprykerplugin.services.resolver.ClassResolverInterface
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.lang.Exception
import java.util.ArrayList

class DocBlockGenerator(
    private val project: Project
) : DocBlockGeneratorInterface {

    @Throws(Exception::class)
    override fun getDocBlockItems(
        classTypes: Array<String>,
        bundleName: String
    ): List<DocBlockItem> {
        val classResolver = project.service<ClassResolverInterface>()
        val docBlockItemList: MutableList<DocBlockItem> = ArrayList<DocBlockItem>()
        for (classType in classTypes) {
            val returnClass: PhpClassInterface? = classResolver.resolveBundleClass(classType, bundleName)
            if (returnClass != null) {
                val returnClassName: String = returnClass.getFullQualifiedName()
                val docBlockMethod = this.getMethodForType(classType)
                val docBlockItem = DocBlockItem(
                    "method",
                    returnClassName,
                    docBlockMethod
                )
                docBlockItemList.add(docBlockItem)
            }
        }

        return docBlockItemList
    }

    @Throws(Exception::class)
    private fun getMethodForType(classType: String): String {
        val classDefinition: ClassDefinitionInterface = project.service<DefinitionProviderInterface>()
            .getDefinitionByType(classType)

        return classDefinition.methodForReturnType
    }
}
