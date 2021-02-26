package com.github.tobi812.sprykerplugin.models.writer

import com.intellij.psi.PsiElement
import java.lang.Exception

interface FileWriterInterface {
    @Throws(Exception::class)
    fun writeFile(filePath: String, fileName: String, phpClassContent: String): PsiElement
}