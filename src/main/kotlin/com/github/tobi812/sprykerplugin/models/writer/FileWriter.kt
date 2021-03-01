package com.github.tobi812.sprykerplugin.models.writer

import com.intellij.openapi.application.Result
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.PlatformVirtualFileManager
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.psi.*
import com.jetbrains.php.lang.PhpFileType

import java.lang.Exception
import java.nio.file.Path

class FileWriter(private val project: Project) : FileWriterInterface {

    @Throws(Exception::class)
    fun writeFile(fileDirectory: PsiDirectory, fileName: String, phpClassContent: String): PsiElement {
        val existingFile = fileDirectory.findFile("$fileName.php")

        if (existingFile != null) {
            throw Exception("$fileName does already exist.")
        }

        val file = PsiFileFactory
            .getInstance(project)
            .createFileFromText("$fileName.php", PhpFileType.INSTANCE, phpClassContent)

        object : WriteCommandAction<Any?>(this.project) {
            @Throws(Throwable::class)
            protected fun run(result: Result<*>) {
                fileDirectory.add(file)
                val virtualFile = fileDirectory.findFile("$fileName.php")
                if (virtualFile != null) {
                    OpenFileDescriptor(project, virtualFile.virtualFile).navigate(true)
                }
            }

            override fun getGroupID(): String {
                return "Create Command"
            }
        }.execute()

        return file
    }
}