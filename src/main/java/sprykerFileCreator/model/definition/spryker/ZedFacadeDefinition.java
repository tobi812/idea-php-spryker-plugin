package pav.sprykerFileCreator.model.definition.spryker;

import pav.sprykerFileCreator.model.definition.ClassDefinitionInterface;
import pav.sprykerFileCreator.model.generator.SprykerConstants;

public class ZedFacadeDefinition implements ClassDefinitionInterface {

    @Override
    public String getClassType() {
        return SprykerConstants.ZED_FACADE;
    }

    @Override
    public String getNamePattern() {
        return "{{ bundleName }}Facade";
    }

    @Override
    public String getNamespacePattern() {
        return "{{ projectName }}\\Zed\\{{ bundleName }}\\Business";
    }

    @Override
    public String getMethodForReturnType() {
        return "getFacade()";
    }

    @Override
    public String[] getDocBlockClasses() {
        String [] docBlockClasses = {
                SprykerConstants.ZED_BUSINESS_FACTORY
        };

        return docBlockClasses;
    }

    @Override
    public String getDefaultParentClass() {
        return "Spryker\\Zed\\Kernel\\Business\\AbstractFacade";
    }

}
