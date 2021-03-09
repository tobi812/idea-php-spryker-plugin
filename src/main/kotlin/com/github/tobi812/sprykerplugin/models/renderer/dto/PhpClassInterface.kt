package com.github.tobi812.sprykerplugin.models.renderer.dto

interface PhpClassInterface : PhpFileInterface {
    val isAbstract: Boolean
    fun setAbstract(): PhpClassInterface

    var parentClass: PhpClassInterface?
    var parentAlias: String?
    fun setParentClass(parentClass: PhpClassInterface): PhpClassInterface
    fun setParentClass(parentClass: PhpClassInterface, aliasPrefix: String): PhpClassInterface

    val useBlockItems: List<UseBlockItem>
    fun addUseBlockItem(useBlockItem: UseBlockItem): PhpClassInterface

    val docBlockItems: List<DocBlockItem>
    fun addDocBlockItem(docBlockItem: DocBlockItem): PhpClassInterface
}