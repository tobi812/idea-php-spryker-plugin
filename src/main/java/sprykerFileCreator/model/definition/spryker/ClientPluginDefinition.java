package pav.sprykerFileCreator.model.definition.spryker;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class ClientPluginDefinition implements ClassDefinitionInterface {

    @Override
    public String getClassType() {
        return SprykerConstants.CLIENT_PLUGIN;
    }

    @Override
    public String getNamePattern() {
        return "{{ className }}Plugin";
    }

    @Override
    public String getNamespacePattern() {
        return "{{ projectName }}\\Client\\{{ bundleName }}\\Plugin";
    }

    @Override
    public String getMethodForReturnType() {
        return "";
    }

    @Override
    public String[] getDocBlockClasses() {
        String [] docBlockClasses = {
                SprykerConstants.CLIENT,
                SprykerConstants.CLIENT_FACTORY,
        };

        return docBlockClasses;
    }

    @Override
    public String getDefaultParentClass() {
        return "Spryker\\Client\\Kernel\\AbstractPlugin";
    }

}
