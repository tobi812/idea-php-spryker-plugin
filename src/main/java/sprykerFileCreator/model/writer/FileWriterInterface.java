package sprykerFileCreator.model.writer;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;

public interface FileWriterInterface {

    PsiElement writeFile(PsiDirectory fileDirectory, String fileName, String phpClassContent) throws Exception;

}
