package sprykerFileCreator.model.definition.spryker;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class ZedConfigDefinition implements ClassDefinitionInterface {

    @Override
    public String getClassType() {
        return SprykerConstants.ZED_CONFIG;
    }

    @Override
    public String getNamePattern() {
        return "{{ bundleName }}Config";
    }

    @Override
    public String getNamespacePattern() {
        return "{{ projectName }}\\Zed\\{{ bundleName }}";
    }

    @Override
    public String getMethodForReturnType() {
        return "getConfig()";
    }

    @Override
    public String[] getDocBlockClasses() {
        String [] docBlockClasses = {};

        return docBlockClasses;
    }

    @Override
    public String getDefaultParentClass() {
        return "Spryker\\Zed\\Kernel\\AbstractBundleConfig";
    }

}
