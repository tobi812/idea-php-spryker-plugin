package com.github.tobi812.sprykerplugin.models.manager;

import com.intellij.psi.PsiElement;

import java.util.HashMap;

public interface ClassManagerInterface {

    PsiElement handleClass(String classType, HashMap<String, String> classConfig) throws Exception;

}
