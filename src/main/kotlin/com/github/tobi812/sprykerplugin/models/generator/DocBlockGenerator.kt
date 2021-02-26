package com.github.tobi812.sprykerplugin.models.generator

import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface
import com.github.tobi812.sprykerplugin.models.definitions.DefinitionProviderInterface
import com.github.tobi812.sprykerplugin.models.renderer.dto.DocBlockItem
import com.github.tobi812.sprykerplugin.models.renderer.dto.PhpClassInterface
import com.github.tobi812.sprykerplugin.models.resolver.ClassResolverInterface
import java.lang.Exception
import java.util.ArrayList

class DocBlockGenerator(
    private val classResolver: ClassResolverInterface,
    private val definitionProvider: DefinitionProviderInterface
) : DocBlockGeneratorInterface {

    @Throws(Exception::class)
    override fun getDocBlockItems(
        classTypes: Array<String>,
        bundleName: String
    ): List<DocBlockItem> {
        val docBlockItemList: MutableList<DocBlockItem> = ArrayList<DocBlockItem>()
        for (classType in classTypes) {
            val returnClass: PhpClassInterface? = this.classResolver.resolveBundleClass(classType, bundleName)
            if (returnClass != null) {
                val returnClassName: String = returnClass.getFullQualifiedName()
                val docBlockMethod = getMethodForType(classType)
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
        val classDefinition: ClassDefinitionInterface = this.definitionProvider.getDefinitionByType(classType)

        return classDefinition.methodForReturnType
    }
}