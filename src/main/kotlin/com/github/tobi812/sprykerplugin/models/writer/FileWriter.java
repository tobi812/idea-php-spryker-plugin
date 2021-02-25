package com.github.tobi812.sprykerplugin.models.writer;

import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.jetbrains.php.lang.PhpFileType;
import org.jetbrains.annotations.NotNull;
import pav.sprykerFileCreator.model.ModelFactory;
import pav.sprykerFileCreator.model.generator.SprykerConstants;
import pav.sprykerFileCreator.model.manager.ClassManagerInterface;
import pav.sprykerFileCreator.model.matcher.ClassTypeMatcher;

import java.util.HashMap;

public class FileWriter implements FileWriterInterface {

    private  Project project;

    public FileWriter(Project project) {
        this.project = project;
    }

    @Override
    public PsiElement writeFile(String filePath, String fileName, String phpClassContent) throws Exception {
        PsiDirectory fileDirectory = this.getFileDirectory(filePath);
        PsiFile existingFile = fileDirectory.findFile(fileName + ".php");

        if (existingFile == null) {
            throw new Exception(fileName + " does already exist.");
        }

        PsiFile file = PsiFileFactory
                .getInstance(project)
                .createFileFromText(fileName + ".php", PhpFileType.INSTANCE, phpClassContent);

        new WriteCommandAction(this.project) {

            @Override
            protected void run(@NotNull Result result) throws Throwable {
                fileDirectory.add(file);

                PsiFile virtualFile = fileDirectory.findFile(fileName + ".php");
                if (virtualFile != null) {
                    new OpenFileDescriptor(project, virtualFile.getVirtualFile()).navigate(true);
                }
            }

            @Override
            public String getGroupID() {
                return "Create Command";
            }

        }.execute();

        return file;
    }

    private PsiDirectory getFileDirectory(String filePath) {

        PsiDirectory currentDir = this.getPsiDirectory();

        String[] pathSegments = filePath.split("/");

        for (String pathSegment : pathSegments) {
            PsiDirectory subDirectory = currentDir.findSubdirectory(pathSegment);

            if (subDirectory == null) {
                subDirectory = currentDir.createSubdirectory(pathSegment);
            }

            currentDir = subDirectory;
        }

        return currentDir;
    }

    private PsiDirectory getPsiDirectory() {
        VirtualFile baseDir = this.project.getBaseDir();

        return PsiManager.getInstance(this.project).findDirectory(baseDir);
    }

}
