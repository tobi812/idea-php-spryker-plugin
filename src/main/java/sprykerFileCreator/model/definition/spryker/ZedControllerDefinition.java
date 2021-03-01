package sprykerFileCreator.model.definition.spryker;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class ZedControllerDefinition implements ClassDefinitionInterface {

    @Override
    public String getClassType() {
        return SprykerConstants.ZED_CONTROLLER;
    }

    @Override
    public String getNamePattern() {
        return "{{ className }}Controller";
    }

    @Override
    public String getNamespacePattern() {
        return "{{ projectName }}\\Zed\\{{ bundleName }}\\Communication\\Controller";
    }

    @Override
    public String getMethodForReturnType() {
        return "";
    }

    @Override
    public String[] getDocBlockClasses() {
        String [] docBlockClasses = {
                SprykerConstants.ZED_COMMUNICATION_FACTORY,
                SprykerConstants.ZED_QUERY_CONTAINER,
                SprykerConstants.ZED_FACADE
        };

        return docBlockClasses;
    }

    @Override
    public String getDefaultParentClass() {
        return "Spryker\\Zed\\Application\\Communication\\Controller\\AbstractController";
    }
}
