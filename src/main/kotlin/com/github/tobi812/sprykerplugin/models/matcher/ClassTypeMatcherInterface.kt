package com.github.tobi812.sprykerplugin.models.matcher

import com.github.tobi812.sprykerplugin.models.definitions.ClassDefinitionInterface
import com.intellij.psi.PsiDirectory
import java.lang.Exception

interface ClassTypeMatcherInterface {
    fun matchClassType(fqClassName: String): ClassDefinitionInterface?

    @Throws(Exception::class)
    fun classTypeMatchesDir(classType: String, directory: PsiDirectory): Boolean

    @Throws(Exception::class)
    fun matchProjectName(classType: String, psiDirectory: PsiDirectory): String
    fun matchProjectName(fqClassName: String): String

    @Throws(Exception::class)
    fun matchBundleName(classType: String, psiDirectory: PsiDirectory): String
    fun matchBundleNameFromFQName(classDefinition: ClassDefinitionInterface, fqName: String): String
}
