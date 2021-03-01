package sprykerFileCreator.model.definition.spryker;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class YvesControllerDefinition implements ClassDefinitionInterface {

    @Override
    public String getClassType() {
        return SprykerConstants.YVES_CONTROLLER;
    }

    @Override
    public String getNamePattern() {
        return "{{ className }}Controller";
    }

    @Override
    public String getNamespacePattern() {
        return "{{ projectName }}\\Yves\\{{ bundleName }}\\Controller";
    }

    @Override
    public String getMethodForReturnType() {
        return "";
    }

    @Override
    public String[] getDocBlockClasses() {
        String [] docBlockClasses = { SprykerConstants.YVES_FACTORY, SprykerConstants.CLIENT };

        return docBlockClasses;
    }

    @Override
    public String getDefaultParentClass() {
        return "Spryker\\Yves\\Application\\Controller\\AbstractController";
    }
}
