package sprykerFileCreator.model.definition.spryker;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class YvesDependencyProviderDefinition implements ClassDefinitionInterface {

    @Override
    public String getClassType() {
        return SprykerConstants.YVES_DEPENDENCY_PROVIDER;
    }

    @Override
    public String getNamePattern() {
        return "{{ bundleName }}DependencyProvider";
    }

    @Override
    public String getNamespacePattern() {
        return "{{ projectName }}\\Yves\\{{ bundleName }}";
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
        return "Spryker\\Yves\\Kernel\\AbstractBundleDependencyProvider";
    }
}
