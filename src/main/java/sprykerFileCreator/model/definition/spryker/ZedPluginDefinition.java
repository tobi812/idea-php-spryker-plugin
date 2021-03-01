package pav.sprykerFileCreator.model.definition.spryker;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class ZedPluginDefinition implements ClassDefinitionInterface {

    @Override
    public String getClassType() {
        return SprykerConstants.ZED_PLUGIN;
    }

    @Override
    public String getNamePattern() {
        return "{{ className }}Plugin";
    }

    @Override
    public String getNamespacePattern() {
        return "{{ projectName }}\\Zed\\{{ bundleName }}\\Communication\\Plugin";
    }

    @Override
    public String getMethodForReturnType() {
        return "";
    }

    @Override
    public String[] getDocBlockClasses() {
        String [] docBlockClasses = {
                SprykerConstants.ZED_COMMUNICATION_FACTORY,
                SprykerConstants.ZED_FACADE,
                SprykerConstants.ZED_CONFIG,
                SprykerConstants.ZED_QUERY_CONTAINER,
        };

        return docBlockClasses;
    }

    @Override
    public String getDefaultParentClass() {
        return "Spryker\\Zed\\Kernel\\Communication\\AbstractPlugin";
    }

}
