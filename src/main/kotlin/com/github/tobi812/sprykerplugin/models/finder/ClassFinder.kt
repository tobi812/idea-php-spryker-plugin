package com.github.tobi812.sprykerplugin.models.finder

import com.github.tobi812.sprykerplugin.models.renderer.dto.PhpClassInterface
import com.github.tobi812.sprykerplugin.models.renderer.dto.PhpClassItem
import com.intellij.openapi.project.Project
import com.jetbrains.php.PhpIndex
import com.jetbrains.php.lang.psi.elements.PhpClass
import java.util.ArrayList

class ClassFinder(private val project: Project) : ClassFinderInterface {
    override fun doesClassExist(fullQualifiedName: String): Boolean {
        val phpClassCollection = findPhpClassCollection(fullQualifiedName)
        return phpClassCollection.isNotEmpty()
    }

    override fun findClass(fullQualifiedCoreName: String): PhpClassInterface? {
        val phpClassCollection = this.findPhpClassCollection(fullQualifiedCoreName)
        if (phpClassCollection.isNotEmpty()) {
            val phpClass = phpClassCollection.iterator().next() ?: return null

            val parentName = phpClass.name
            val namespace = phpClass.namespaceName

            return PhpClassItem(namespace, parentName)
        }

        return null
    }

    override fun findPhpClassCollection(fullQualifiedName: String): Collection<PhpClass?> {
        return try {
            val phpIndex = PhpIndex.getInstance(project)
            phpIndex.getAnyByFQN(fullQualifiedName)
        } catch (exception: IllegalStateException) {
            ArrayList()
        }
    }
}
