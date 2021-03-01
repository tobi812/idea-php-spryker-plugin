package sprykerFileCreator.model.definition.spryker;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class ZedBusinessFactoryDefinition implements ClassDefinitionInterface {

    @Override
    public String getClassType() {
        return SprykerConstants.ZED_BUSINESS_FACTORY;
    }

    @Override
    public String getNamePattern() {
        return "{{ bundleName }}BusinessFactory";
    }

    @Override
    public String getNamespacePattern() {
        return "{{ projectName }}\\Zed\\{{ bundleName }}\\Business";
    }

    @Override
    public String getMethodForReturnType() {
        return "getFactory()";
    }

    @Override
    public String[] getDocBlockClasses() {
        String [] docBlockClasses = {
                SprykerConstants.ZED_CONFIG,
                SprykerConstants.ZED_QUERY_CONTAINER
        };

        return docBlockClasses;
    }

    @Override
    public String getDefaultParentClass() {
        return "Spryker\\Zed\\Kernel\\Business\\AbstractBusinessFactory";
    }

}
