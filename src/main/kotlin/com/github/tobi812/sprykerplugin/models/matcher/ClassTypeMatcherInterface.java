package com.github.tobi812.sprykerplugin.models.matcher;

import com.intellij.psi.PsiDirectory;
import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;

public interface ClassTypeMatcherInterface {

    ClassDefinitionInterface matchClassType(String fqClassName);

    Boolean classTypeMatchesDir(String classType, PsiDirectory directory) throws Exception;

    String matchProjectName(String classType, PsiDirectory psiDirectory) throws Exception;

    String matchProjectName(String fqClassName);

    String matchBundleName(String classType, PsiDirectory psiDirectory) throws Exception;

    String matchBundleNameFromFQName(ClassDefinitionInterface classDefinition, String fqName);

}
