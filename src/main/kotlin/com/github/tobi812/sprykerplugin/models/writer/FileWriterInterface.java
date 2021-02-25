package com.github.tobi812.sprykerplugin.models.writer;

import com.intellij.psi.PsiElement;

public interface FileWriterInterface {

    PsiElement writeFile(String filePath, String fileName, String phpClassContent) throws Exception;

}
