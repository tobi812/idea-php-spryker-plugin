package com.github.tobi812.sprykerplugin.services.writer

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.jetbrains.php.lang.PhpFileType
import java.lang.Exception

class FileWriter(private val project: Project) : FileWriterInterface {

    @Throws(Exception::class)
    override fun writeFile(fileDirectory: PsiDirectory, fileName: String, phpClassContent: String): PsiElement {
        val existingFile = fileDirectory.findFile("$fileName.php")

        if (existingFile != null) {
            throw Exception("$fileName does already exist.")
        }

        val file = PsiFileFactory
            .getInstance(project)
            .createFileFromText("$fileName.php", PhpFileType.INSTANCE, phpClassContent)

        WriteCommandAction.writeCommandAction(this.project, file)
            .withGroupId("Create Command")
            .run<Throwable> {
                fileDirectory.add(file)
                val virtualFile = fileDirectory.findFile("$fileName.php")
                if (virtualFile != null) {
                    OpenFileDescriptor(project, virtualFile.virtualFile).navigate(true)
                }
            }

        return file
    }

    @Throws(Exception::class)
    override fun createSubdirectory(fileDirectory: PsiDirectory, subDirectoryName: String): PsiDirectory? {
        WriteCommandAction.writeCommandAction(this.project)
            .withGroupId("Create Command")
            .run<Throwable> {
                fileDirectory.createSubdirectory(subDirectoryName)
            }

        return fileDirectory.findSubdirectory(subDirectoryName)
    }
}
