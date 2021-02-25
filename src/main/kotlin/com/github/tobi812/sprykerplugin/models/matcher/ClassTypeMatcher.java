package com.github.tobi812.sprykerplugin.models.matcher;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDirectory;
import org.apache.commons.lang.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.definition.DefinitionProviderInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;
import java.util.Collection;
import java.util.regex.Pattern;

public class ClassTypeMatcher implements ClassTypeMatcherInterface {

    private static final String WILDCARD = ".*";

    private DefinitionProviderInterface definitionProvider;

    public ClassTypeMatcher(DefinitionProviderInterface definitionProvider) {
        this.definitionProvider = definitionProvider;
    }

    @Override
    public ClassDefinitionInterface matchClassType(String fqClassName) {
        Collection<ClassDefinitionInterface> classDefinitions = this.definitionProvider
                .getAllClassDefinitions()
                .values();

        for (ClassDefinitionInterface classDefinition : classDefinitions) {
            String classTypePattern = this.getClassTypePattern(classDefinition);
            Boolean matched = Pattern.matches(classTypePattern, fqClassName);

            if (matched) {
                return classDefinition;
            }
        }

        return null;
    }

    private String getClassTypePattern(ClassDefinitionInterface classDefinition) {
        String namePattern = this.getNamePattern(classDefinition, false);
        String namespacePattern = this.getNamespacePattern(classDefinition, WILDCARD, false);

        return namespacePattern +  "\\\\" + namePattern;
    }

//    public ArrayList<String> matchClassTypesByDir(PsiDirectory directory) {
//        Collection<ClassDefinitionInterface> classDefinitions = this.definitionProvider
//                .getAllClassDefinitions()
//                .values();
//
//        ArrayList<String> matchedTypes = new ArrayList<String>();
//        String dir = directory.toString();
//        dir = dir.replace("/", "\\");
//
//        for (ClassDefinitionInterface classDefinition : classDefinitions) {
//
//            String bundleName = this.matchBundleName(classDefinition.getClassType(), directory);
//            String namespacePattern = this.getNamespacePattern(classDefinition, );
//
//            Boolean matched = Pattern.matches("^.*" + namespacePattern + WILDCARD, dir);
//            if (matched) {
//                matchedTypes.add(classDefinition.getClassType());
//            }
//        }
//
//        return matchedTypes;
//    }

    @Override
    public Boolean classTypeMatchesDir(String classType, PsiDirectory directory) throws Exception {
        ClassDefinitionInterface classDefinition = definitionProvider.getDefinitionByType(classType);
        String dir = directory.toString();
        dir = dir.replace("/", "\\");
        String bundleName = this.matchBundleName(classType, directory);
        String namespacePattern = this.getNamespacePattern(classDefinition, bundleName, true);
        namespacePattern = "^.*" + namespacePattern + "$";
        Boolean matched = Pattern.matches(namespacePattern, dir);

        return matched;
    }

    @Override
    public String matchProjectName(String classType, PsiDirectory psiDirectory) throws Exception {
        String namespacePattern = this.definitionProvider.getDefinitionByType(classType).getNamespacePattern();
        Integer projectPosition = this.getPlaceholderPosition(namespacePattern, SprykerConstants.PROJECT_NAME_PLACEHOLDER);

        PsiDirectory currentDirectory = psiDirectory;
        for ( Integer i = 1; i < projectPosition; i++ ) {
            currentDirectory = currentDirectory.getParent();
        }

        if (currentDirectory == null) {
            return "";
        }

        return currentDirectory.getName();
    }

    @Override
    public String matchProjectName(String fqClassName) {
        String[] classSegments = fqClassName.split("\\\\");

        return classSegments[1];
    }

    @Override
    public String matchBundleName(String classType, PsiDirectory psiDirectory) throws Exception {
        String namespacePattern = this.definitionProvider.getDefinitionByType(classType).getNamespacePattern();
        Integer bundlePosition = this.getPlaceholderPosition(namespacePattern, SprykerConstants.BUNDLE_NAME_PLACEHOLDER);

        PsiDirectory currentDirectory = psiDirectory;
        for ( Integer i = 1; i < bundlePosition; i++ ) {
            currentDirectory = currentDirectory.getParent();
        }

        if (currentDirectory == null) {
            return "";
        }

        return currentDirectory.getName();
    }

    public String matchBundleNameFromFQName(ClassDefinitionInterface classDefinition, String fqName) {
        String namespacePattern = classDefinition.getNamespacePattern();
        Integer bundlePosition = this.getPlaceholderPosition(namespacePattern, SprykerConstants.BUNDLE_NAME_PLACEHOLDER);
        String[] nameSegments = fqName.split("\\\\");
        ArrayUtils.reverse(nameSegments);

        String bundleName = "";
        Integer currentPosition = 0;
        for (String nameSegment: nameSegments) {
            if (bundlePosition.equals(currentPosition)) {
                bundleName = nameSegment;
                break;
            }
            currentPosition++;
        }

        return bundleName;
    }

    private Integer getPlaceholderPosition(String namespacePattern, String bundleNamePlaceholder) {
        String[] segments = namespacePattern.split("\\\\");
        ArrayUtils.reverse(segments);

        Integer bundlePosition = 0;
        Integer segmentCount = 0;
        for (String segment : segments) {
            segmentCount++;
            if (segment.equals(bundleNamePlaceholder)) {
                bundlePosition = segmentCount;
            }
        }
        return bundlePosition;
    }

    @NotNull
    private String getNamePattern(ClassDefinitionInterface classDefinition, Boolean trim) {
        String namePattern = classDefinition.getNamePattern();

        namePattern = namePattern.replace(SprykerConstants.BUNDLE_NAME_PLACEHOLDER, WILDCARD);
        namePattern = namePattern.replace(SprykerConstants.CLASS_NAME_PLACEHOLDER, WILDCARD);
        namePattern = namePattern.replace("\\", "\\\\");

        if (trim) {
            namePattern = StringUtil.trimEnd(namePattern, WILDCARD);
            namePattern = StringUtil.trimStart(namePattern, WILDCARD);
        }

        return namePattern;
    }

    @NotNull
    private String getNamespacePattern(ClassDefinitionInterface classDefinition, String bundleName, Boolean trim) {
        String namespacePattern = classDefinition.getNamespacePattern();

        namespacePattern = namespacePattern.replace(SprykerConstants.BUNDLE_NAME_PLACEHOLDER, bundleName);
        namespacePattern = namespacePattern.replace(SprykerConstants.PROJECT_NAME_PLACEHOLDER, WILDCARD);
        namespacePattern = namespacePattern.replace("\\", "\\\\");

        if (trim) {
            namespacePattern = StringUtil.trimStart(namespacePattern, WILDCARD);
            namespacePattern = StringUtil.trimEnd(namespacePattern, WILDCARD);
        }

        return namespacePattern;
    }

}
