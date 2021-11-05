package sprykerFileCreator.model.manager;

import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;

import java.util.HashMap;

public interface ClassManagerInterface {

    PsiElement handleClass(PsiDirectory fileDirectory, String classType, HashMap<String, String> classConfig) throws Exception;

}
