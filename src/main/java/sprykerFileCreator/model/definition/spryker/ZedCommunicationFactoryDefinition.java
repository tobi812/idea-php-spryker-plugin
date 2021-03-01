package sprykerFileCreator.model.definition.spryker;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class ZedCommunicationFactoryDefinition implements ClassDefinitionInterface {

    @Override
    public String getClassType() {
        return SprykerConstants.ZED_COMMUNICATION_FACTORY;
    }

    @Override
    public String getNamePattern() {
        return "{{ bundleName }}CommunicationFactory";
    }

    @Override
    public String getNamespacePattern() {
        return "{{ projectName }}\\Zed\\{{ bundleName }}\\Communication";
    }

    @Override
    public String getMethodForReturnType() {
        return "getFactory()";
    }

    @Override
    public String[] getDocBlockClasses() {
        String [] docBlockClasses = {
                SprykerConstants.ZED_QUERY_CONTAINER,
                SprykerConstants.ZED_FACADE,
                SprykerConstants.ZED_CONFIG
        };

        return docBlockClasses;
    }

    @Override
    public String getDefaultParentClass() {
        return "Spryker\\Zed\\Kernel\\Communication\\AbstractCommunicationFactory";
    }

}
