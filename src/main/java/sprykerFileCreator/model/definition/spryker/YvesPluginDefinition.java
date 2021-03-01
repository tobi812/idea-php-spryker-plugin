package pav.sprykerFileCreator.model.definition.spryker;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class YvesPluginDefinition implements ClassDefinitionInterface {

    @Override
    public String getClassType() {
        return SprykerConstants.YVES_PLUGIN;
    }

    @Override
    public String getNamePattern() {
        return "{{ className }}Plugin";
    }

    @Override
    public String getNamespacePattern() {
        return "{{ projectName }}\\Yves\\{{ bundleName }}\\Plugin";
    }

    @Override
    public String getMethodForReturnType() {
        return "";
    }

    @Override
    public String[] getDocBlockClasses() {
        String [] docBlockClasses = {
                SprykerConstants.YVES_FACTORY,
                SprykerConstants.CLIENT,
        };

        return docBlockClasses;
    }

    @Override
    public String getDefaultParentClass() {
        return "Spryker\\Yves\\Kernel\\AbstractPlugin";
    }

}
