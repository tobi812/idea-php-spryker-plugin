package com.github.tobi812.sprykerplugin.models.manager

import com.github.tobi812.sprykerplugin.actions.ClassConfig
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import java.lang.Exception

interface ClassManagerInterface {
    @Throws(Exception::class)
    fun handleClass(
        fileDirectory: PsiDirectory,
        classType: String,
        classConfig: ClassConfig
    ): PsiElement
}