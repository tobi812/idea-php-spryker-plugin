package com.github.tobi812.sprykerplugin.models.renderer.dto

import com.intellij.openapi.util.text.StringUtil
import java.util.ArrayList

class PhpClassItem(override val namespace: String, override val name: String) : PhpClassInterface {
    override var isAbstract: Boolean = false
    override var parentClass: PhpClassInterface? = null
    override var parentAlias = ""
    override val useBlockItems: MutableList<UseBlockItem>
    override val docBlockItems: MutableList<DocBlockItem>

    init {
        this.useBlockItems = ArrayList()
        this.docBlockItems = ArrayList()
    }

    override fun getFullQualifiedName(): String {
        return StringUtil.trimEnd(this.namespace, "\\") + "\\" + this.name
    }

    override fun setAbstract(): PhpClassInterface {
        this.isAbstract = true

        return this
    }

    override fun setParentClass(parentClass: PhpClassInterface): PhpClassInterface {
        return this.setParentClass(parentClass, "Spryker")
    }

    override fun setParentClass(parentClass: PhpClassInterface, aliasPrefix: String): PhpClassInterface {
        val parentClassName = parentClass.name
        var parentNamespace = parentClass.namespace
        parentNamespace = StringUtil.trimStart(parentNamespace, "\\")
        val parentUseItem = UseBlockItem(parentClassName, parentNamespace)

        if (parentClassName == name) {
//            if (parentNamespace.equals(this.namespace)) {
//                throw new Exception();
//            }
            parentAlias = aliasPrefix + parentClassName
            parentUseItem.alias = parentAlias
        }

        this.addUseBlockItem(parentUseItem)
        this.parentClass = parentClass

        return this
    }

    override fun addUseBlockItem(useBlockItem: UseBlockItem): PhpClassInterface {
        this.useBlockItems.add(useBlockItem)

        return this
    }

    override fun addDocBlockItem(docBlockItem: DocBlockItem): PhpClassInterface {
        this.docBlockItems.add(docBlockItem)

        return this
    }
}