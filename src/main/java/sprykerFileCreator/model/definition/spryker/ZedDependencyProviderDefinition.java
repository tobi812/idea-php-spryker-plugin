package sprykerFileCreator.model.definition.spryker;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class ZedDependencyProviderDefinition implements ClassDefinitionInterface {

    @Override
    public String getClassType() {
        return SprykerConstants.ZED_DEPENDENCY_PROVIDER;
    }

    @Override
    public String getNamePattern() {
        return "{{ bundleName }}DependencyProvider";
    }

    @Override
    public String getNamespacePattern() {
        return "{{ projectName }}\\Zed\\{{ bundleName }}";
    }

    @Override
    public String getMethodForReturnType() {
        return "";
    }

    @Override
    public String[] getDocBlockClasses() {
        String [] docBlockClasses = {};

        return docBlockClasses;
    }

    @Override
    public String getDefaultParentClass() {
        return "Spryker\\Zed\\Kernel\\AbstractBundleDependencyProvider";
    }
}
