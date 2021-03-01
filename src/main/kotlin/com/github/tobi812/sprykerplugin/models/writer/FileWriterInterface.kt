package com.github.tobi812.sprykerplugin.models.writer

import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import java.lang.Exception

interface FileWriterInterface {
    @Throws(Exception::class)
    fun writeFile(fileDirectory: PsiDirectory, fileName: String, phpClassContent: String): PsiElement
}