package com.github.tobi812.sprykerplugin.models.writer

import com.intellij.openapi.application.Result
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.jetbrains.php.lang.PhpFileType

import java.lang.Exception

class FileWriter(private val project: Project) : FileWriterInterface {
    @Throws(Exception::class)
    override fun writeFile(filePath: String, fileName: String, phpClassContent: String): PsiElement {
        val fileDirectory = getFileDirectory(filePath)
        val existingFile = fileDirectory.findFile("$fileName.php")
            ?: throw Exception("$fileName does already exist.")
        val file = PsiFileFactory
            .getInstance(this.project)
            .createFileFromText("$fileName.php", PhpFileType.INSTANCE, phpClassContent)

        object : WriteCommandAction<Any?>(this.project) {
            @Throws(Throwable::class)
            override fun run(result: Result<*>) {
                fileDirectory.add(file)
                val virtualFile = fileDirectory.findFile("$fileName.php")
                if (virtualFile != null) {
                    OpenFileDescriptor(this.project, virtualFile.virtualFile).navigate(true)
                }
            }

            override fun getGroupID(): String {
                return "Create Command"
            }
        }.execute()
        return file
    }

    private fun getFileDirectory(filePath: String): PsiDirectory {
        var currentDir = this.getPsiDirectory()
        val pathSegments = filePath.split("/".toRegex()).toTypedArray()
        for (pathSegment in pathSegments) {
            var subDirectory = currentDir!!.findSubdirectory(pathSegment)
            if (subDirectory == null) {
                subDirectory = currentDir.createSubdirectory(pathSegment)
            }

            currentDir = subDirectory
        }

        return currentDir
    }

    private fun getPsiDirectory(): PsiDirectory? {
        val baseDir = this.project.baseDir
        return PsiManager.getInstance(this.project).findDirectory(baseDir)
    }
}